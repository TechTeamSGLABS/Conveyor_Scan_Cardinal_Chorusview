package com.example.barcodescanner.utils

import android.graphics.RectF
import android.util.Log
import kotlinx.coroutines.*
import kotlin.math.*

/**
 * 🚀 ADVANCED Moving Barcode Association Manager
 *
 * Handles association of barcodes on moving conveyors with multiple strategies:
 * ✅ Extended time windows with smart buffering
 * ✅ Positional/spatial association tracking
 * ✅ Sequence-based pattern detection
 * ✅ Movement prediction and tracking
 * ✅ Multi-pass association with confidence scoring
 * ✅ Conveyor speed adaptation
 */
class MovingBarcodeAssociationManager {

    companion object {
        private const val TAG = "MovingBarcodeAssociation"

        // 🚀 ENHANCED: Extended timeouts for moving barcodes
        private const val IMMEDIATE_ASSOCIATION_WINDOW_MS = 2000L // 2 seconds for same-frame detection
        private const val EXTENDED_ASSOCIATION_WINDOW_MS = 8000L // 8 seconds for moving conveyor
        private const val SEQUENCE_ASSOCIATION_WINDOW_MS = 15000L // 15 seconds for pattern detection
        private const val POSITION_TOLERANCE_PIXELS = 200f // Distance tolerance for spatial association

        // Movement prediction constants
        private const val MIN_SPEED_THRESHOLD = 50f // pixels per second
        private const val MAX_SPEED_THRESHOLD = 500f // pixels per second
        private const val PREDICTION_TIME_MS = 3000L // How far ahead to predict
    }

    // 🚀 ENHANCED: Barcode detection with spatial and temporal data
    data class BarcodeDetection(
        val value: String,
        val format: Int,
        val timestamp: Long,
        val position: RectF,
        val screenCenter: PointF,
        val confidence: Float = 1.0f,
        var isAssociated: Boolean = false,
        var associationConfidence: Float = 0f,
        val movementVector: PointF? = null // Predicted movement direction
    ) {
        fun age(currentTime: Long): Long = currentTime - timestamp
        fun distanceTo(other: BarcodeDetection): Float {
            return sqrt(
                (screenCenter.x - other.screenCenter.x).pow(2) +
                        (screenCenter.y - other.screenCenter.y).pow(2)
            )
        }
    }

    // 🚀 ENHANCED: Association candidate with confidence scoring
    data class AssociationCandidate(
        val toteId: BarcodeDetection,
        val olpn: BarcodeDetection,
        val associationType: AssociationType,
        val confidence: Float,
        val reason: String
    )

    enum class AssociationType {
        IMMEDIATE_SAME_FRAME,    // Detected in same frame
        TEMPORAL_SEQUENCE,       // Detected in sequence within time window
        SPATIAL_PROXIMITY,       // Detected in nearby positions
        MOVEMENT_PREDICTION,     // Predicted based on movement patterns
        PATTERN_BASED           // Based on historical patterns
    }

    // Internal state
    private val recentDetections = mutableListOf<BarcodeDetection>()
    private val pendingAssociations = mutableListOf<AssociationCandidate>()
    private val movementTracker = ConveyorMovementTracker()
    private val sequenceAnalyzer = BarcodeSequenceAnalyzer()

    // Callbacks
    private var onAssociationFound: ((String, String, AssociationType, Float, String) -> Unit)? = null
    private var onMissedAssociation: ((BarcodeDetection, String) -> Unit)? = null

    fun setCallbacks(
        onAssociation: (toteId: String, olpn: String, type: AssociationType, confidence: Float, reason: String) -> Unit,
        onMissed: (detection: BarcodeDetection, reason: String) -> Unit
    ) {
        onAssociationFound = onAssociation
        onMissedAssociation = onMissed
    }

    /**
     * 🚀 MAIN: Process new barcode detection with all association strategies
     */
    suspend fun processDetection(
        value: String,
        format: Int,
        boundingBox: RectF,
        currentTime: Long = System.currentTimeMillis()
    ) {
        val detection = createBarcodeDetection(value, format, boundingBox, currentTime)

        // Add to recent detections
        synchronized(recentDetections) {
            recentDetections.add(detection)
        }

        // Update movement tracking
        movementTracker.addDetection(detection)

        // Update sequence analysis
        sequenceAnalyzer.addDetection(detection)

        Log.d(TAG, "🔍 Processing detection: $value (${getBarcodeTypeName(format)}) at ${detection.screenCenter}")

        // 🚀 STRATEGY 1: Immediate same-frame association (highest priority)
        findImmediateAssociations(detection, currentTime)

        // 🚀 STRATEGY 2: Temporal sequence association
        findTemporalAssociations(detection, currentTime)

        // 🚀 STRATEGY 3: Spatial proximity association
        findSpatialAssociations(detection, currentTime)

        // 🚀 STRATEGY 4: Movement prediction association
        findMovementPredictionAssociations(detection, currentTime)

        // 🚀 STRATEGY 5: Pattern-based association
        findPatternBasedAssociations(detection, currentTime)

        // Clean up old detections
        cleanupOldDetections(currentTime)

        // Process pending associations
        processPendingAssociations(currentTime)
    }

    /**
     * 🚀 STRATEGY 1: Immediate same-frame associations (traditional approach)
     */
    private fun findImmediateAssociations(detection: BarcodeDetection, currentTime: Long) {
        val recentOlpns = getRecentDetectionsByType(true, IMMEDIATE_ASSOCIATION_WINDOW_MS, currentTime)
        val recentToteIds = getRecentDetectionsByType(false, IMMEDIATE_ASSOCIATION_WINDOW_MS, currentTime)

        when {
            BarcodeFormatReference.isOlpnFormat(detection.format) -> {
                // New OLPN detected, look for recent ToteIDs
                recentToteIds.filter { !it.isAssociated }.forEach { toteId ->
                    val candidate = AssociationCandidate(
                        toteId = toteId,
                        olpn = detection,
                        associationType = AssociationType.IMMEDIATE_SAME_FRAME,
                        confidence = calculateImmediateConfidence(toteId, detection),
                        reason = "Same-frame detection (${currentTime - toteId.timestamp}ms apart)"
                    )
                    pendingAssociations.add(candidate)
                }
            }
            BarcodeFormatReference.isToteIdFormat(detection.format) -> {
                // New ToteID detected, look for recent OLPNs
                recentOlpns.filter { !it.isAssociated }.forEach { olpn ->
                    val candidate = AssociationCandidate(
                        toteId = detection,
                        olpn = olpn,
                        associationType = AssociationType.IMMEDIATE_SAME_FRAME,
                        confidence = calculateImmediateConfidence(detection, olpn),
                        reason = "Same-frame detection (${currentTime - olpn.timestamp}ms apart)"
                    )
                    pendingAssociations.add(candidate)
                }
            }
        }
    }

    /**
     * 🚀 STRATEGY 2: Temporal sequence association (extended time windows)
     */
    private fun findTemporalAssociations(detection: BarcodeDetection, currentTime: Long) {
        val extendedOlpns = getRecentDetectionsByType(true, EXTENDED_ASSOCIATION_WINDOW_MS, currentTime)
        val extendedToteIds = getRecentDetectionsByType(false, EXTENDED_ASSOCIATION_WINDOW_MS, currentTime)

        when {
            BarcodeFormatReference.isOlpnFormat(detection.format) -> {
                extendedToteIds.filter { !it.isAssociated && it.age(currentTime) > IMMEDIATE_ASSOCIATION_WINDOW_MS }.forEach { toteId ->
                    val candidate = AssociationCandidate(
                        toteId = toteId,
                        olpn = detection,
                        associationType = AssociationType.TEMPORAL_SEQUENCE,
                        confidence = calculateTemporalConfidence(toteId, detection, currentTime),
                        reason = "Temporal sequence (${currentTime - toteId.timestamp}ms delay)"
                    )
                    pendingAssociations.add(candidate)
                }
            }
            BarcodeFormatReference.isToteIdFormat(detection.format) -> {
                extendedOlpns.filter { !it.isAssociated && it.age(currentTime) > IMMEDIATE_ASSOCIATION_WINDOW_MS }.forEach { olpn ->
                    val candidate = AssociationCandidate(
                        toteId = detection,
                        olpn = olpn,
                        associationType = AssociationType.TEMPORAL_SEQUENCE,
                        confidence = calculateTemporalConfidence(detection, olpn, currentTime),
                        reason = "Temporal sequence (${currentTime - olpn.timestamp}ms delay)"
                    )
                    pendingAssociations.add(candidate)
                }
            }
        }
    }

    /**
     * 🚀 STRATEGY 3: Spatial proximity association (position-based)
     */
    private fun findSpatialAssociations(detection: BarcodeDetection, currentTime: Long) {
        val allRecent = getRecentDetections(EXTENDED_ASSOCIATION_WINDOW_MS, currentTime)

        allRecent.filter {
            !it.isAssociated &&
                    it.value != detection.value &&
                    isComplementaryType(detection, it) &&
                    detection.distanceTo(it) <= POSITION_TOLERANCE_PIXELS
        }.forEach { complement ->

            val (toteId, olpn) = if (BarcodeFormatReference.isToteIdFormat(detection.format)) {
                Pair(detection, complement)
            } else {
                Pair(complement, detection)
            }

            val candidate = AssociationCandidate(
                toteId = toteId,
                olpn = olpn,
                associationType = AssociationType.SPATIAL_PROXIMITY,
                confidence = calculateSpatialConfidence(detection, complement),
                reason = "Spatial proximity (${detection.distanceTo(complement).toInt()}px apart)"
            )
            pendingAssociations.add(candidate)
        }
    }

    /**
     * 🚀 STRATEGY 4: Movement prediction association
     */
    private fun findMovementPredictionAssociations(detection: BarcodeDetection, currentTime: Long) {
        val predictedPositions = movementTracker.getPredictedPositions(PREDICTION_TIME_MS)
        val unresolvedDetections = getRecentDetections(EXTENDED_ASSOCIATION_WINDOW_MS, currentTime)
            .filter { !it.isAssociated && isComplementaryType(detection, it) }

        unresolvedDetections.forEach { unresolved ->
            predictedPositions[unresolved.value]?.let { predictedPos ->
                val distance = sqrt(
                    (detection.screenCenter.x - predictedPos.x).pow(2) +
                            (detection.screenCenter.y - predictedPos.y).pow(2)
                )

                if (distance <= POSITION_TOLERANCE_PIXELS) {
                    val (toteId, olpn) = if (BarcodeFormatReference.isToteIdFormat(detection.format)) {
                        Pair(detection, unresolved)
                    } else {
                        Pair(unresolved, detection)
                    }

                    val candidate = AssociationCandidate(
                        toteId = toteId,
                        olpn = olpn,
                        associationType = AssociationType.MOVEMENT_PREDICTION,
                        confidence = calculateMovementPredictionConfidence(detection, unresolved, distance),
                        reason = "Movement prediction (${distance.toInt()}px from predicted position)"
                    )
                    pendingAssociations.add(candidate)
                }
            }
        }
    }

    /**
     * 🚀 STRATEGY 5: Pattern-based association
     */
    private fun findPatternBasedAssociations(detection: BarcodeDetection, currentTime: Long) {
        val patterns = sequenceAnalyzer.detectPatterns(detection, currentTime)

        patterns.forEach { pattern ->
            val candidate = AssociationCandidate(
                toteId = pattern.toteId,
                olpn = pattern.olpn,
                associationType = AssociationType.PATTERN_BASED,
                confidence = pattern.confidence,
                reason = "Pattern-based (${pattern.description})"
            )
            pendingAssociations.add(candidate)
        }
    }

    /**
     * 🚀 PROCESS: Evaluate and execute pending associations
     */
    private suspend fun processPendingAssociations(currentTime: Long) {
        if (pendingAssociations.isEmpty()) return

        // Group by barcode pair and select best candidate
        val groupedCandidates = pendingAssociations.groupBy { "${it.toteId.value}-${it.olpn.value}" }

        groupedCandidates.forEach { (pairKey, candidates) ->
            // Select candidate with highest confidence
            val bestCandidate = candidates.maxByOrNull {
                it.confidence * getTypeWeight(it.associationType)
            }

            bestCandidate?.let { candidate ->
                if (candidate.confidence >= getMinimumConfidenceThreshold(candidate.associationType)) {
                    // Execute association
                    executeAssociation(candidate)

                    // Mark as associated
                    candidate.toteId.isAssociated = true
                    candidate.olpn.isAssociated = true
                    candidate.toteId.associationConfidence = candidate.confidence
                    candidate.olpn.associationConfidence = candidate.confidence

                    Log.d(TAG, "✅ ASSOCIATION EXECUTED: ${candidate.toteId.value} ↔ ${candidate.olpn.value}")
                    Log.d(TAG, "   Type: ${candidate.associationType}, Confidence: ${candidate.confidence}")
                    Log.d(TAG, "   Reason: ${candidate.reason}")
                }
            }
        }

        // Clear processed candidates
        pendingAssociations.clear()
    }

    /**
     * 🚀 CONFIDENCE: Calculate confidence scores for different association types
     */
    private fun calculateImmediateConfidence(toteId: BarcodeDetection, olpn: BarcodeDetection): Float {
        val timeDiff = abs(toteId.timestamp - olpn.timestamp)
        val timeScore = (1.0f - (timeDiff / IMMEDIATE_ASSOCIATION_WINDOW_MS.toFloat())).coerceIn(0f, 1f)
        val distanceScore = (1.0f - (toteId.distanceTo(olpn) / POSITION_TOLERANCE_PIXELS)).coerceIn(0f, 1f)
        return (timeScore * 0.7f + distanceScore * 0.3f)
    }

    private fun calculateTemporalConfidence(toteId: BarcodeDetection, olpn: BarcodeDetection, currentTime: Long): Float {
        val maxAge = maxOf(toteId.age(currentTime), olpn.age(currentTime))
        val ageScore = (1.0f - (maxAge / EXTENDED_ASSOCIATION_WINDOW_MS.toFloat())).coerceIn(0f, 1f)
        val sequenceScore = sequenceAnalyzer.getSequenceConfidence(toteId, olpn)
        return (ageScore * 0.6f + sequenceScore * 0.4f)
    }

    private fun calculateSpatialConfidence(detection1: BarcodeDetection, detection2: BarcodeDetection): Float {
        val distance = detection1.distanceTo(detection2)
        val distanceScore = (1.0f - (distance / POSITION_TOLERANCE_PIXELS)).coerceIn(0f, 1f)
        val movementScore = movementTracker.getMovementConsistencyScore(detection1, detection2)
        return (distanceScore * 0.7f + movementScore * 0.3f)
    }

    private fun calculateMovementPredictionConfidence(
        detection: BarcodeDetection,
        predicted: BarcodeDetection,
        distance: Float
    ): Float {
        val distanceScore = (1.0f - (distance / POSITION_TOLERANCE_PIXELS)).coerceIn(0f, 1f)
        val speedConsistency = movementTracker.getSpeedConsistencyScore(detection, predicted)
        return (distanceScore * 0.6f + speedConsistency * 0.4f)
    }

    /**
     * 🚀 UTILITIES: Helper functions
     */
    private fun createBarcodeDetection(value: String, format: Int, boundingBox: RectF, timestamp: Long): BarcodeDetection {
        val center = PointF(boundingBox.centerX(), boundingBox.centerY())
        val movementVector = movementTracker.predictMovementVector(value, center, timestamp)

        return BarcodeDetection(
            value = value,
            format = format,
            timestamp = timestamp,
            position = boundingBox,
            screenCenter = center,
            movementVector = movementVector
        )
    }

    private fun getRecentDetectionsByType(isOlpn: Boolean, windowMs: Long, currentTime: Long): List<BarcodeDetection> {
        return synchronized(recentDetections) {
            recentDetections.filter { detection ->
                detection.age(currentTime) <= windowMs &&
                        if (isOlpn) BarcodeFormatReference.isOlpnFormat(detection.format)
                        else BarcodeFormatReference.isToteIdFormat(detection.format)
            }
        }
    }

    private fun getRecentDetections(windowMs: Long, currentTime: Long): List<BarcodeDetection> {
        return synchronized(recentDetections) {
            recentDetections.filter { it.age(currentTime) <= windowMs }
        }
    }

    private fun isComplementaryType(detection1: BarcodeDetection, detection2: BarcodeDetection): Boolean {
        val d1IsOlpn = BarcodeFormatReference.isOlpnFormat(detection1.format)
        val d2IsOlpn = BarcodeFormatReference.isOlpnFormat(detection2.format)
        val d1IsToteId = BarcodeFormatReference.isToteIdFormat(detection1.format)
        val d2IsToteId = BarcodeFormatReference.isToteIdFormat(detection2.format)

        return (d1IsOlpn && d2IsToteId) || (d1IsToteId && d2IsOlpn)
    }

    private fun getTypeWeight(type: AssociationType): Float {
        return when (type) {
            AssociationType.IMMEDIATE_SAME_FRAME -> 1.0f
            AssociationType.TEMPORAL_SEQUENCE -> 0.8f
            AssociationType.SPATIAL_PROXIMITY -> 0.9f
            AssociationType.MOVEMENT_PREDICTION -> 0.7f
            AssociationType.PATTERN_BASED -> 0.6f
        }
    }

    private fun getMinimumConfidenceThreshold(type: AssociationType): Float {
        return when (type) {
            AssociationType.IMMEDIATE_SAME_FRAME -> 0.5f
            AssociationType.TEMPORAL_SEQUENCE -> 0.6f
            AssociationType.SPATIAL_PROXIMITY -> 0.7f
            AssociationType.MOVEMENT_PREDICTION -> 0.6f
            AssociationType.PATTERN_BASED -> 0.5f
        }
    }

    private fun executeAssociation(candidate: AssociationCandidate) {
        onAssociationFound?.invoke(
            candidate.toteId.value,
            candidate.olpn.value,
            candidate.associationType,
            candidate.confidence,
            candidate.reason
        )
    }

    private fun cleanupOldDetections(currentTime: Long) {
        synchronized(recentDetections) {
            recentDetections.removeAll { detection ->
                val shouldRemove = detection.age(currentTime) > SEQUENCE_ASSOCIATION_WINDOW_MS
                if (shouldRemove && !detection.isAssociated) {
                    onMissedAssociation?.invoke(detection, "Timeout after ${detection.age(currentTime)}ms")
                }
                shouldRemove
            }
        }

        movementTracker.cleanup(currentTime)
        sequenceAnalyzer.cleanup(currentTime)
    }

    private fun getBarcodeTypeName(format: Int): String {
        return when {
            BarcodeFormatReference.isOlpnFormat(format) -> "OLPN"
            BarcodeFormatReference.isToteIdFormat(format) -> "ToteID"
            else -> "Unknown"
        }
    }

    /**
     * 🚀 STATS: Get association statistics
     */
    fun getAssociationStats(): AssociationStats {
        val total = recentDetections.size
        val associated = recentDetections.count { it.isAssociated }
        val pending = total - associated

        return AssociationStats(
            totalDetections = total,
            associatedCount = associated,
            pendingCount = pending,
            associationRate = if (total > 0) (associated.toFloat() / total) else 0f
        )
    }

    data class AssociationStats(
        val totalDetections: Int,
        val associatedCount: Int,
        val pendingCount: Int,
        val associationRate: Float
    )

    /**
     * 🚀 CLEANUP: Clear all state
     */
    fun reset() {
        synchronized(recentDetections) {
            recentDetections.clear()
        }
        pendingAssociations.clear()
        movementTracker.reset()
        sequenceAnalyzer.reset()
    }
}

// 🚀 SUPPORTING CLASSES

/**
 * Tracks conveyor movement patterns and predicts barcode positions
 */
class ConveyorMovementTracker {
    private val positionHistory = mutableMapOf<String, MutableList<PositionRecord>>()

    data class PositionRecord(
        val position: PointF,
        val timestamp: Long
    )

    fun addDetection(detection: MovingBarcodeAssociationManager.BarcodeDetection) {
        val history = positionHistory.getOrPut(detection.value) { mutableListOf() }
        history.add(PositionRecord(detection.screenCenter, detection.timestamp))

        // Keep only recent history
        val cutoff = detection.timestamp - 10000L // 10 seconds
        history.removeAll { it.timestamp < cutoff }
    }

    fun predictMovementVector(value: String, currentPos: PointF, timestamp: Long): PointF? {
        val history = positionHistory[value] ?: return null
        if (history.size < 2) return null

        val recent = history.takeLast(2)
        val deltaX = recent[1].position.x - recent[0].position.x
        val deltaY = recent[1].position.y - recent[0].position.y
        val deltaTime = recent[1].timestamp - recent[0].timestamp

        if (deltaTime <= 0) return null

        return PointF(deltaX / deltaTime * 1000f, deltaY / deltaTime * 1000f) // pixels per second
    }

    fun getPredictedPositions(timeAheadMs: Long): Map<String, PointF> {
        val predictions = mutableMapOf<String, PointF>()
        val currentTime = System.currentTimeMillis()

        positionHistory.forEach { (value, history) ->
            if (history.size >= 2) {
                val latest = history.last()
                val vector = predictMovementVector(value, latest.position, latest.timestamp)
                vector?.let { v ->
                    val timeDelta = timeAheadMs / 1000f
                    predictions[value] = PointF(
                        latest.position.x + v.x * timeDelta,
                        latest.position.y + v.y * timeDelta
                    )
                }
            }
        }

        return predictions
    }

    fun getMovementConsistencyScore(detection1: MovingBarcodeAssociationManager.BarcodeDetection, detection2: MovingBarcodeAssociationManager.BarcodeDetection): Float {
        // Check if barcodes are moving in consistent directions
        val vector1 = detection1.movementVector
        val vector2 = detection2.movementVector

        if (vector1 == null || vector2 == null) return 0.5f

        // Calculate angle similarity
        val dot = vector1.x * vector2.x + vector1.y * vector2.y
        val mag1 = sqrt(vector1.x * vector1.x + vector1.y * vector1.y)
        val mag2 = sqrt(vector2.x * vector2.x + vector2.y * vector2.y)

        if (mag1 == 0f || mag2 == 0f) return 0.5f

        val cosAngle = dot / (mag1 * mag2)
        return (cosAngle + 1f) / 2f // Convert from [-1,1] to [0,1]
    }

    fun getSpeedConsistencyScore(detection1: MovingBarcodeAssociationManager.BarcodeDetection, detection2: MovingBarcodeAssociationManager.BarcodeDetection): Float {
        val vector1 = detection1.movementVector ?: return 0.5f
        val vector2 = detection2.movementVector ?: return 0.5f

        val speed1 = sqrt(vector1.x * vector1.x + vector1.y * vector1.y)
        val speed2 = sqrt(vector2.x * vector2.x + vector2.y * vector2.y)

        val speedDiff = abs(speed1 - speed2)
        val avgSpeed = (speed1 + speed2) / 2f

        if (avgSpeed == 0f) return 1f

        return (1f - (speedDiff / avgSpeed)).coerceIn(0f, 1f)
    }

    fun cleanup(currentTime: Long) {
        val cutoff = currentTime - 30000L // 30 seconds
        positionHistory.values.forEach { history ->
            history.removeAll { it.timestamp < cutoff }
        }
        positionHistory.entries.removeAll { it.value.isEmpty() }
    }

    fun reset() {
        positionHistory.clear()
    }
}

/**
 * Analyzes barcode sequences and patterns for association
 */
class BarcodeSequenceAnalyzer {
    private val detectionSequence = mutableListOf<MovingBarcodeAssociationManager.BarcodeDetection>()

    data class SequencePattern(
        val toteId: MovingBarcodeAssociationManager.BarcodeDetection,
        val olpn: MovingBarcodeAssociationManager.BarcodeDetection,
        val confidence: Float,
        val description: String
    )

    fun addDetection(detection: MovingBarcodeAssociationManager.BarcodeDetection) {
        detectionSequence.add(detection)

        // Keep only recent sequence
        val cutoff = detection.timestamp - 20000L // 20 seconds
        detectionSequence.removeAll { it.timestamp < cutoff }
    }

    fun detectPatterns(newDetection: MovingBarcodeAssociationManager.BarcodeDetection, currentTime: Long): List<SequencePattern> {
        val patterns = mutableListOf<SequencePattern>()

        // Pattern 1: Alternating sequence (OLPN-ToteID-OLPN-ToteID...)
        patterns.addAll(detectAlternatingPattern(newDetection))

        // Pattern 2: Paired sequence (OLPN-ToteID pairs appear together)
        patterns.addAll(detectPairedPattern(newDetection))

        // Pattern 3: Consistent timing intervals
        patterns.addAll(detectTimingPattern(newDetection))

        return patterns
    }

    private fun detectAlternatingPattern(newDetection: MovingBarcodeAssociationManager.BarcodeDetection): List<SequencePattern> {
        if (detectionSequence.size < 3) return emptyList()

        val recent = detectionSequence.takeLast(4) // Look at last 4 detections
        val patterns = mutableListOf<SequencePattern>()

        // Check for alternating OLPN-ToteID pattern
        for (i in 0 until recent.size - 1) {
            val current = recent[i]
            val next = recent[i + 1]

            if (isComplementarySequence(current, next) && !current.isAssociated && !next.isAssociated) {
                val (toteId, olpn) = if (BarcodeFormatReference.isToteIdFormat(current.format)) {
                    Pair(current, next)
                } else {
                    Pair(next, current)
                }

                patterns.add(SequencePattern(
                    toteId = toteId,
                    olpn = olpn,
                    confidence = 0.7f,
                    description = "Alternating sequence pattern"
                ))
            }
        }

        return patterns
    }

    private fun detectPairedPattern(newDetection: MovingBarcodeAssociationManager.BarcodeDetection): List<SequencePattern> {
        // Look for pairs that appear close together in time
        val recent = detectionSequence.filter {
            abs(it.timestamp - newDetection.timestamp) <= 5000L && // Within 5 seconds
                    !it.isAssociated &&
                    it.value != newDetection.value
        }

        return recent.filter { isComplementarySequence(newDetection, it) }
            .map { complement ->
                val (toteId, olpn) = if (BarcodeFormatReference.isToteIdFormat(newDetection.format)) {
                    Pair(newDetection, complement)
                } else {
                    Pair(complement, newDetection)
                }

                SequencePattern(
                    toteId = toteId,
                    olpn = olpn,
                    confidence = 0.6f,
                    description = "Paired sequence pattern"
                )
            }
    }

    private fun detectTimingPattern(newDetection: MovingBarcodeAssociationManager.BarcodeDetection): List<SequencePattern> {
        // Look for consistent timing intervals between complementary barcodes
        val complementary = detectionSequence.filter {
            isComplementarySequence(newDetection, it) &&
                    !it.isAssociated
        }

        return complementary.filter { complement ->
            val timeDiff = abs(newDetection.timestamp - complement.timestamp)
            timeDiff in 1000L..6000L // Between 1-6 seconds apart
        }.map { complement ->
            val (toteId, olpn) = if (BarcodeFormatReference.isToteIdFormat(newDetection.format)) {
                Pair(newDetection, complement)
            } else {
                Pair(complement, newDetection)
            }

            SequencePattern(
                toteId = toteId,
                olpn = olpn,
                confidence = 0.5f,
                description = "Consistent timing pattern"
            )
        }
    }

    private fun isComplementarySequence(detection1: MovingBarcodeAssociationManager.BarcodeDetection, detection2: MovingBarcodeAssociationManager.BarcodeDetection): Boolean {
        val d1IsOlpn = BarcodeFormatReference.isOlpnFormat(detection1.format)
        val d2IsOlpn = BarcodeFormatReference.isOlpnFormat(detection2.format)
        val d1IsToteId = BarcodeFormatReference.isToteIdFormat(detection1.format)
        val d2IsToteId = BarcodeFormatReference.isToteIdFormat(detection2.format)

        return (d1IsOlpn && d2IsToteId) || (d1IsToteId && d2IsOlpn)
    }

    fun getSequenceConfidence(toteId: MovingBarcodeAssociationManager.BarcodeDetection, olpn: MovingBarcodeAssociationManager.BarcodeDetection): Float {
        // Analyze how well this pair fits existing sequence patterns
        val timeDiff = abs(toteId.timestamp - olpn.timestamp)

        // Prefer pairs detected closer in time
        val timeScore = when {
            timeDiff <= 1000L -> 1.0f
            timeDiff <= 3000L -> 0.8f
            timeDiff <= 6000L -> 0.6f
            else -> 0.3f
        }

        return timeScore
    }

    fun cleanup(currentTime: Long) {
        val cutoff = currentTime - 30000L // 30 seconds
        detectionSequence.removeAll { it.timestamp < cutoff }
    }

    fun reset() {
        detectionSequence.clear()
    }
}

data class PointF(val x: Float, val y: Float)