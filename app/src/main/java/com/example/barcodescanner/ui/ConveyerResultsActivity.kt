package com.example.barcodescanner.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barcodescanner.R
import com.example.barcodescanner.adapter.AssociationAdapter
import com.example.barcodescanner.data.local.AppDatabase
import com.example.barcodescanner.data.local.AssociationEntity
import com.example.barcodescanner.data.local.getDisplayStatus
import com.example.barcodescanner.service.CloudSyncService
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 🚀 FIXED: Results screen for Conveyer Scan sessions
 * ✅ Resolved "No Data Found" false positive issue
 * ✅ Proper async data loading with loading states
 * ✅ Better debugging and error handling
 */
class ConveyerResultsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ConveyerResultsActivity"
    }

    // UI Components
    private lateinit var currentSessionCard: View
    private lateinit var currentSessionTitleTextView: TextView
    private lateinit var currentSessionStatsTextView: TextView
    private lateinit var viewModeSwitch: Switch
    private lateinit var viewModeLabel: TextView
    private lateinit var allSessionsTitleTextView: TextView
    private lateinit var syncStatusTextView: TextView
    private lateinit var associationsRecyclerView: RecyclerView
    private lateinit var backToScannerButton: Button
    private lateinit var doneButton: Button
    private lateinit var clearDataButton: Button
    private lateinit var forceSyncButton: Button

    // Data Management
    private var sessionId: Long = 0L
    private lateinit var associationAdapter: AssociationAdapter
    private val conveyerViewModel: ConveyerScanViewModel by viewModels()

    // View State
    private var showCurrentSessionOnly = false
    private var currentSessionAssociations = listOf<AssociationEntity>()
    private var allAssociations = listOf<AssociationEntity>()

    // 🚀 FIXED: Add loading state tracking
    private var isCurrentSessionLoaded = false
    private var isAllDataLoaded = false
    private var hasShownEmptyStateCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conveyer_results)

        // Get session ID from intent with debugging
        sessionId = intent.getLongExtra("session_id", 0L)
        val associationCount = intent.getIntExtra("association_count", -1)
        val barcodeCount = intent.getIntExtra("barcode_count", -1)

        Log.d(TAG, "🔍 RESULTS ACTIVITY STARTED:")
        Log.d(TAG, "   Session ID: $sessionId")
        Log.d(TAG, "   Expected Associations: $associationCount")
        Log.d(TAG, "   Expected Barcodes: $barcodeCount")

        // Setup action bar
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Conveyer Scan Records"
            subtitle = if (sessionId > 0) "Session: $sessionId" else "All Sessions"
        }

        initializeViews()
        setupUI()

        // 🚀 FIXED: Load data and wait for completion before checking empty state
        loadAllDataWithProperSequencing()
        observeViewModel()
    }

    private fun initializeViews() {
        // Current Session Card
        currentSessionCard = findViewById(R.id.currentSessionCard)
        currentSessionTitleTextView = findViewById(R.id.currentSessionTitleTextView)
        currentSessionStatsTextView = findViewById(R.id.currentSessionStatsTextView)

        // View Mode Controls
        viewModeSwitch = findViewById(R.id.viewModeSwitch)
        viewModeLabel = findViewById(R.id.viewModeLabel)
        allSessionsTitleTextView = findViewById(R.id.allSessionsTitleTextView)

        // Status and Controls
        syncStatusTextView = findViewById(R.id.syncStatusTextView)
        associationsRecyclerView = findViewById(R.id.associationsRecyclerView)
        backToScannerButton = findViewById(R.id.backToScannerButton)
        doneButton = findViewById(R.id.doneButton)
        clearDataButton = findViewById(R.id.clearDataButton)
        forceSyncButton = findViewById(R.id.forceSyncButton)

        // Setup RecyclerView with enhanced adapter
        associationAdapter = AssociationAdapter(
            onItemClick = { association -> showAssociationDetails(association) },
            onRetryClick = { association -> retryAssociation(association) }
        )

        associationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ConveyerResultsActivity)
            adapter = associationAdapter
        }
    }

    private fun setupUI() {
        // Set current session title
        setupCurrentSessionCard()

        // Setup view mode toggle
        setupViewModeToggle()

        // Setup button listeners
        backToScannerButton.setOnClickListener {
            finish() // Return to scanner
        }

        doneButton.setOnClickListener {
            navigateToMainDashboard()
        }

        clearDataButton.setOnClickListener {
            showClearDataConfirmation()
        }

        forceSyncButton.setOnClickListener {
            forceSyncNow()
        }
    }

    private fun setupCurrentSessionCard() {
        if (sessionId > 0) {
            val sessionDate = java.text.SimpleDateFormat("MMM dd, yyyy HH:mm", java.util.Locale.getDefault())
                .format(java.util.Date(sessionId))
            currentSessionTitleTextView.text = "📦 Current Session\n$sessionDate"
            currentSessionCard.visibility = View.VISIBLE
        } else {
            currentSessionCard.visibility = View.GONE
        }
    }

    private fun setupViewModeToggle() {
        // Initialize toggle state
        viewModeSwitch.isChecked = showCurrentSessionOnly
        updateViewModeLabel()

        viewModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            showCurrentSessionOnly = isChecked
            updateViewModeLabel()
            // 🚀 FIXED: Only refresh if data is loaded
            if (isDataLoadingComplete()) {
                refreshDisplayedData()
            }
        }
    }

    private fun updateViewModeLabel() {
        if (showCurrentSessionOnly && sessionId > 0) {
            viewModeLabel.text = "📋 Current Session Only"
            allSessionsTitleTextView.text = "Current Session Associations"
        } else {
            viewModeLabel.text = "📚 All Sessions History"
            allSessionsTitleTextView.text = "All Historical Sessions"
        }
    }

    // 🚀 FIXED: Proper data loading sequencing
    private fun loadAllDataWithProperSequencing() {
        Log.d(TAG, "🔄 Starting data loading sequence...")

        // Show loading state
        currentSessionStatsTextView.text = "⏳ Loading session data..."

        // Reset loading flags
        isCurrentSessionLoaded = false
        isAllDataLoaded = false
        hasShownEmptyStateCheck = false

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val db = AppDatabase.getInstance(this@ConveyerResultsActivity)
                val dao = db.associationDao()

                // 1. Load current session data if we have a session ID
                if (sessionId > 0) {
                    Log.d(TAG, "🔍 Loading current session data for session: $sessionId")
                    val sessionAssociations = dao.getAssociationsBySession(sessionId)

                    withContext(Dispatchers.Main) {
                        currentSessionAssociations = sessionAssociations
                        isCurrentSessionLoaded = true
                        Log.d(TAG, "✅ Current session loaded: ${sessionAssociations.size} associations")

                        // Update UI immediately for current session
                        updateCurrentSessionStats(sessionAssociations)
                        checkIfLoadingCompleteAndRefresh()
                    }
                } else {
                    // No specific session, mark as loaded
                    isCurrentSessionLoaded = true
                }

                // 2. Load all historical data
                Log.d(TAG, "🔍 Loading all historical data...")
                val allAssocs = dao.getAllAssociations()

                withContext(Dispatchers.Main) {
                    allAssociations = allAssocs
                    isAllDataLoaded = true
                    Log.d(TAG, "✅ All data loaded: ${allAssocs.size} total associations")

                    // Update overall statistics
                    loadOverallStatistics()
                    checkIfLoadingCompleteAndRefresh()
                }

            } catch (e: Exception) {
                Log.e(TAG, "❌ Failed to load data", e)
                withContext(Dispatchers.Main) {
                    currentSessionStatsTextView.text = "❌ Error loading data: ${e.message}"
                    Toast.makeText(this@ConveyerResultsActivity, "Failed to load data: ${e.message}", Toast.LENGTH_LONG).show()

                    // Mark as loaded even if failed to prevent infinite loading
                    isCurrentSessionLoaded = true
                    isAllDataLoaded = true
                    checkIfLoadingCompleteAndRefresh()
                }
            }
        }
    }

    // 🚀 FIXED: Check if loading is complete before showing UI
    private fun isDataLoadingComplete(): Boolean {
        return isCurrentSessionLoaded && isAllDataLoaded
    }

    // 🚀 FIXED: Only refresh UI after loading is complete
    private fun checkIfLoadingCompleteAndRefresh() {
        if (isDataLoadingComplete()) {
            Log.d(TAG, "🎉 All data loading complete, refreshing display...")
            refreshDisplayedData()
        } else {
            Log.d(TAG, "⏳ Still loading... Current: $isCurrentSessionLoaded, All: $isAllDataLoaded")
        }
    }

    // 🚀 FIXED: Update current session stats immediately when loaded
    private fun updateCurrentSessionStats(sessionAssociations: List<AssociationEntity>) {
        if (sessionAssociations.isNotEmpty()) {
            val currentStats = """
                📊 Current Session: ${sessionAssociations.size} associations
                ✅ Data successfully loaded from database
                🕐 Last updated: ${java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())}
            """.trimIndent()
            currentSessionStatsTextView.text = currentStats
        } else if (sessionId > 0) {
            currentSessionStatsTextView.text = "📊 Current Session: No associations found\n⚠️ This session may not have any completed associations yet."
        }
    }

    private fun loadOverallStatistics() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val db = AppDatabase.getInstance(this@ConveyerResultsActivity)
                val dao = db.associationDao()

                // Use existing methods from your DAO
                val totalAssociations = allAssociations.size
                val uniqueSessions = allAssociations.map { it.sessionId }.distinct().size
                val submittedCount = dao.getSubmittedCount()
                val pendingCount = dao.getPendingCount()

                withContext(Dispatchers.Main) {
                    val overallStats = """
                        📊 Overall Statistics (ALL DATA):
                        • Total Associations: $totalAssociations
                        • Total Sessions: $uniqueSessions
                        • Submitted: $submittedCount
                        • Pending Sync: $pendingCount
                        
                        ✅ All associations shown regardless of sync status
                    """.trimIndent()

                    // Update current session stats if we have session data
                    if (sessionId > 0 && currentSessionAssociations.isNotEmpty()) {
                        val currentStats = "📊 Current Session: ${currentSessionAssociations.size} associations"
                        currentSessionStatsTextView.text = "$currentStats\n\n$overallStats"
                    } else if (sessionId > 0) {
                        val currentStats = "📊 Current Session: No associations found"
                        currentSessionStatsTextView.text = "$currentStats\n\n$overallStats"
                    } else {
                        currentSessionStatsTextView.text = overallStats
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load overall statistics", e)
                withContext(Dispatchers.Main) {
                    // Fallback to basic stats
                    currentSessionStatsTextView.text = "Statistics unavailable"
                }
            }
        }
    }

    private fun observeViewModel() {
        // Note: We're primarily using direct database access now, but keep ViewModel for sync status

        // Observe sync status
        conveyerViewModel.syncStatus.observe(this) { status ->
            syncStatusTextView.text = status
        }

        // Observe errors
        conveyerViewModel.errorMessage.observe(this) { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                conveyerViewModel.clearError()
            }
        }
    }

    // 🚀 FIXED: Improved refresh logic with proper empty state checking
    private fun refreshDisplayedData() {
        if (!isDataLoadingComplete()) {
            Log.d(TAG, "⏳ Data still loading, skipping refresh...")
            return
        }

        val associationsToShow = if (showCurrentSessionOnly && sessionId > 0) {
            Log.d(TAG, "📋 Showing current session only: ${currentSessionAssociations.size} associations")
            currentSessionAssociations
        } else {
            Log.d(TAG, "📚 Showing all associations: ${allAssociations.size} associations")
            allAssociations
        }

        // Group associations by session for better display
        val groupedAssociations = if (showCurrentSessionOnly) {
            associationsToShow
        } else {
            // Sort by session ID (newest first) for all sessions view
            associationsToShow.sortedByDescending { it.sessionId }
        }

        Log.d(TAG, "🔄 Refreshing display with ${groupedAssociations.size} associations")
        associationAdapter.updateAssociations(groupedAssociations)

        // 🚀 FIXED: Only show empty state after data loading is complete AND we haven't shown it yet
        if (groupedAssociations.isEmpty() && !hasShownEmptyStateCheck) {
            hasShownEmptyStateCheck = true
            Log.w(TAG, "⚠️ No associations found after complete data load - showing empty state")
            showEmptyState()
        } else if (groupedAssociations.isNotEmpty()) {
            Log.d(TAG, "✅ Data successfully displayed: ${groupedAssociations.size} associations")
        }

        // Update UI labels based on what's being shown
        updateDisplayCounts(associationsToShow)
    }

    private fun updateDisplayCounts(associations: List<AssociationEntity>) {
        val currentModeText = if (showCurrentSessionOnly && sessionId > 0) {
            "Showing ${associations.size} associations from current session (ALL data, sync ignored)"
        } else {
            val sessionCount = associations.map { it.sessionId }.distinct().size
            "Showing ${associations.size} associations across $sessionCount sessions (ALL data, sync ignored)"
        }

        Log.d(TAG, "📊 $currentModeText")
    }

    // 🚀 FIXED: Better empty state messaging with debugging info
    private fun showEmptyState() {
        val debugInfo = """
            Debug Information:
            • Session ID: $sessionId
            • Current Session Loaded: $isCurrentSessionLoaded (${currentSessionAssociations.size} items)
            • All Data Loaded: $isAllDataLoaded (${allAssociations.size} items)
            • Show Current Only: $showCurrentSessionOnly
            • Loading Complete: ${isDataLoadingComplete()}
        """.trimIndent()

        Log.w(TAG, "⚠️ SHOWING EMPTY STATE:")
        Log.w(TAG, debugInfo)

        val emptyMessage = if (showCurrentSessionOnly && sessionId > 0) {
            """
            No associations found for current session.
            """.trimIndent()
        } else {
            """
            No associations found in database.
            """.trimIndent()
        }

        AlertDialog.Builder(this)
            .setTitle("No Data Found")
            .setMessage(emptyMessage)
            .setPositiveButton("Start New Scan") { _, _ ->
                finish()
            }
            .setNegativeButton("Go to Dashboard") { _, _ ->
                navigateToMainDashboard()
            }
            .setNeutralButton("Retry Load") { _, _ ->
                // Reset and reload data
                hasShownEmptyStateCheck = false
                loadAllDataWithProperSequencing()
            }
            .setIcon(R.drawable.ic_info)
            .show()
    }

    private fun showAssociationDetails(association: AssociationEntity) {
        val sessionDate = java.text.SimpleDateFormat("MMM dd, yyyy HH:mm:ss", java.util.Locale.getDefault())
            .format(java.util.Date(association.sessionId))

        val details = """
            🔗 Association Details
            
            ToteID: ${association.toteId}
            OLPN: ${association.olpn}
            
            📅 Session: $sessionDate
            🕐 Detected: ${formatTimestamp(association.timestamp)}
            🔄 Status: ${association.getDisplayStatus()}
            🔁 Retry Count: ${association.retryCount}
            
            ${if (association.submitted) "✅ Submitted: ${formatTimestamp(association.submitTimestamp ?: 0L)}" else "⏳ Pending submission"}
            ${if (association.errorMessage != null) "❌ Error: ${association.errorMessage}" else ""}
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Association Details")
            .setMessage(details)
            .setPositiveButton("OK", null)
            .setNeutralButton("View Session") { _, _ ->
                // Switch to current session only mode for this association's session
                if (association.sessionId != sessionId) {
                    showSessionAssociations(association.sessionId)
                }
            }
            .setIcon(R.drawable.ic_info)
            .show()
    }

    private fun showSessionAssociations(targetSessionId: Long) {
        AlertDialog.Builder(this)
            .setTitle("View Session")
            .setMessage("Load all associations from session: ${formatSessionTimestamp(targetSessionId)}?")
            .setPositiveButton("View Session") { _, _ ->
                // Create new intent to view specific session
                val intent = Intent(this, ConveyerResultsActivity::class.java).apply {
                    putExtra("session_id", targetSessionId)
                }
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun retryAssociation(association: AssociationEntity) {
        AlertDialog.Builder(this)
            .setTitle("Retry Submission")
            .setMessage("Force retry submission for this association?\n\nToteID: ${association.toteId}\nOLPN: ${association.olpn}")
            .setPositiveButton("Retry") { _, _ ->
                // Reset retry count and error message in database
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val db = AppDatabase.getInstance(this@ConveyerResultsActivity)
                        val dao = db.associationDao()

                        // Reset association for retry
                        val updatedAssociation = association.copy(
                            submitted = false,
                            errorMessage = null,
                            retryCount = 0,
                            submitTimestamp = null
                        )

                        dao.updateAssociation(updatedAssociation)

                        withContext(Dispatchers.Main) {
                            forceSyncNow()
                            Toast.makeText(this@ConveyerResultsActivity, "Retry triggered for association", Toast.LENGTH_SHORT).show()
                            // Refresh data
                            loadAllDataWithProperSequencing()
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to reset association for retry", e)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ConveyerResultsActivity, "Failed to reset association: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .setIcon(R.drawable.ic_scan)
            .show()
    }

    private fun showClearDataConfirmation() {
        val message = if (showCurrentSessionOnly && sessionId > 0) {
            "Clear data for current session only?\n\nThis will delete ${currentSessionAssociations.size} associations from this session."
        } else {
            "Clear ALL association data?\n\nThis will delete ${allAssociations.size} associations from ${allAssociations.map { it.sessionId }.distinct().size} sessions permanently."
        }

        AlertDialog.Builder(this)
            .setTitle("⚠️ Clear Data")
            .setMessage(message)
            .setPositiveButton("Clear Current Session") { _, _ ->
                if (sessionId > 0) {
                    conveyerViewModel.deleteSessionData(sessionId)
                    Toast.makeText(this, "Current session data cleared", Toast.LENGTH_SHORT).show()
                    loadAllDataWithProperSequencing()
                }
            }
            .setNeutralButton("Clear ALL Data") { _, _ ->
                showConfirmClearAllData()
            }
            .setNegativeButton("Cancel", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun showConfirmClearAllData() {
        AlertDialog.Builder(this)
            .setTitle("⚠️ DANGER: Clear All Data")
            .setMessage("This will permanently delete ALL ${allAssociations.size} associations from ALL sessions. This action cannot be undone!\n\nAre you absolutely sure?")
            .setPositiveButton("YES, DELETE ALL") { _, _ ->
                conveyerViewModel.deleteAllAssociations()
                Toast.makeText(this, "All data cleared successfully", Toast.LENGTH_SHORT).show()
                loadAllDataWithProperSequencing()
            }
            .setNegativeButton("Cancel", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun forceSyncNow() {
        conveyerViewModel.forceSyncNow()

        val intent = Intent(this, CloudSyncService::class.java).apply {
            action = CloudSyncService.ACTION_FORCE_SYNC
        }
        startService(intent)

        Toast.makeText(this, "🔄 Force sync triggered for all associations", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainDashboard() {
        try {
            // Try to get launcher intent first
            val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(launchIntent)
                finish()
                return
            }

            // Fallback
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to navigate to main dashboard", e)
            finish()
        }
    }

    private fun formatTimestamp(timestamp: Long): String {
        return if (timestamp > 0) {
            java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
                .format(java.util.Date(timestamp))
        } else {
            "N/A"
        }
    }

    private fun formatSessionTimestamp(timestamp: Long): String {
        return try {
            val formatter = java.text.SimpleDateFormat("MMM dd, yyyy, HH:mm", java.util.Locale.getDefault())
            formatter.format(java.util.Date(timestamp))
        } catch (e: Exception) {
            "Invalid"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onResume() {
        super.onResume()
        // 🚀 FIXED: Only refresh if we've already loaded data once
        if (isDataLoadingComplete()) {
            Log.d(TAG, "🔄 Activity resumed - refreshing data...")
            loadAllDataWithProperSequencing()
        }
    }
}