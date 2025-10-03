package com.example.barcodescanner.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.barcodescanner.MainActivity  // ✅ FIXED: Correct import path
import com.example.barcodescanner.R

/**
 * 🔄 UPDATED: Main Dashboard with new Conveyer Scan module
 *
 * Now includes three scanning modes:
 * 1. Live View (Real-time scanning)
 * 2. Photo Capture (High-resolution capture)
 * 3. 🚀 NEW: Conveyer Scan (24/7 unattended automation)
 */
class DashboardActivity : AppCompatActivity() {

    // UI Components - Existing
    private lateinit var liveViewCard: CardView
    private lateinit var photoCaptureCard: CardView
    private lateinit var liveViewButton: Button
    private lateinit var photoCaptureButton: Button
    private lateinit var liveViewDescription: TextView
    private lateinit var photoCaptureDescription: TextView

    // UI Components - NEW (Optional until layout is updated)
    private var conveyerScanCard: CardView? = null
    private var conveyerScanButton: Button? = null
    private var conveyerScanDescription: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Setup action bar
        supportActionBar?.apply {
            title = "QICSCAN Dashboard"
            subtitle = "Choose Your Scanning Method"
        }

        initializeViews()
        setupDescriptions()
        setupClickListeners()
    }

    private fun initializeViews() {
        // Initialize existing cards (required)
        liveViewCard = findViewById(R.id.liveViewCard)
        photoCaptureCard = findViewById(R.id.photoCaptureCard)

        // Initialize existing buttons (required)
        liveViewButton = findViewById(R.id.liveViewButton)
        photoCaptureButton = findViewById(R.id.photoCaptureButton)

        // Initialize existing description text views (required)
        liveViewDescription = findViewById(R.id.liveViewDescription)
        photoCaptureDescription = findViewById(R.id.photoCaptureDescription)

        // 🚀 NEW: Initialize Conveyer Scan elements (optional - only if they exist in layout)
        try {
            conveyerScanCard = findViewById(R.id.conveyerScanCard)
            conveyerScanButton = findViewById(R.id.conveyerScanButton)
            conveyerScanDescription = findViewById(R.id.conveyerScanDescription)
        } catch (e: Exception) {
            // Elements don't exist in layout yet - that's okay
        }
    }

    private fun setupDescriptions() {
        liveViewDescription.text = """
            Real-time barcode detection with instant feedback. Perfect for quick scans and immediate results. Supports zoom and autofocus for optimal detection.
        """.trimIndent()

        photoCaptureDescription.text = """
        
        """.trimIndent()

        // 🚀 NEW: Conveyer Scan description (only if element exists)
        conveyerScanDescription?.text = """
            Unattended scanning with automatic OLPN-ToteID association. Perfect for conveyer belt operations with cloud sync.
        """.trimIndent()
    }

    private fun setupClickListeners() {
        // Live View navigation
        liveViewCard.setOnClickListener { navigateToLiveView() }
        liveViewButton.setOnClickListener { navigateToLiveView() }

        // Photo Capture navigation
        photoCaptureCard.setOnClickListener { navigateToPhotoCapture() }
        photoCaptureButton.setOnClickListener { navigateToPhotoCapture() }

        // 🚀 NEW: Conveyer Scan navigation (only if elements exist)
        conveyerScanCard?.setOnClickListener { navigateToConveyerScan() }
        conveyerScanButton?.setOnClickListener { navigateToConveyerScan() }
    }

    private fun navigateToLiveView() {
        val intent = Intent(this, MainActivity::class.java).apply {  // ✅ FIXED: Correct class reference
            putExtra("SCAN_MODE", "LIVE_VIEW")
        }
        startActivity(intent)
    }

    private fun navigateToPhotoCapture() {
        // ✅ FIXED: Safe navigation even if PhotoCaptureActivity doesn't exist yet
        try {
            val intent = Intent(this, Class.forName("com.example.barcodescanner.ui.PhotoCaptureActivity")).apply {
                putExtra("SCAN_MODE", "PHOTO_CAPTURE")
            }
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            // PhotoCaptureActivity doesn't exist yet, fall back to MainActivity
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("SCAN_MODE", "PHOTO_CAPTURE")
            }
            startActivity(intent)
        }
    }

    // 🚀 NEW: Navigate to Conveyer Scan
    private fun navigateToConveyerScan() {
        // ✅ FIXED: Safe navigation even if ConveyerScanActivity doesn't exist yet
        try {
            val intent = Intent(this, Class.forName("com.example.barcodescanner.ui.ConveyerScanActivity")).apply {
                putExtra("SCAN_MODE", "CONVEYER_SCAN")
            }
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            // ConveyerScanActivity doesn't exist yet, fall back to MainActivity
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("SCAN_MODE", "CONVEYER_SCAN")
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // You can add analytics or state refresh here if needed
    }
}