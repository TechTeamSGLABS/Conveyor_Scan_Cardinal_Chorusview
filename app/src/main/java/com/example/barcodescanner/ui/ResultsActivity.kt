package com.example.barcodescanner.ui

import android.app.ProgressDialog
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.setPadding
import androidx.lifecycle.lifecycleScope
import com.example.barcodescanner.R
import com.example.barcodescanner.data.local.AppDatabase
import com.example.barcodescanner.data.remote.ChorusApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class ResultsActivity : AppCompatActivity() {

    private lateinit var resultsContainer: LinearLayout
    private lateinit var doneButton: Button
    private var currentSessionId: Long = 0L
    private var scanCompleted = false
    private lateinit var chorusApiService: ChorusApiService
    private var boxedImagePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        // 🔙 SIMPLE FIX: Try ActionBar first, but don't rely on it
        try {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
                title = "Scanned Barcodes"
            }
        } catch (e: Exception) {
            Log.e("ResultsActivity", "ActionBar setup failed", e)
        }

        setResult(RESULT_OK)

        currentSessionId = intent.getLongExtra("session_id", 0L)
        boxedImagePath = intent.getStringExtra("boxed_image_path")

        resultsContainer = findViewById(R.id.resultsContainer)
        doneButton = findViewById(R.id.doneButton)

        chorusApiService = ChorusApiService()

        // 🔙 GUARANTEED FIX: Always add visible back button at top
        addVisibleBackButton()

        loadResults()

        // 🛑 FIX 3: Direct submit button - no need for intermediate "Done" step
        doneButton.text = "📤 Submit Data"
        doneButton.setBackgroundColor(Color.parseColor("#2196F3"))

        doneButton.setOnClickListener {
            showSubmitConfirmationDialog()
        }

        val clearDataButton: Button = findViewById(R.id.clearDataButton)
        clearDataButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Clear All Data?")
                .setMessage("This will delete all scanned barcode records. Proceed?")
                .setPositiveButton("Yes") { _, _ ->
                    lifecycleScope.launch {
                        AppDatabase.getInstance(this@ResultsActivity).barcodeDao().deleteAll()
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ResultsActivity, "Data cleared", Toast.LENGTH_SHORT).show()
                            loadResults()
                        }
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    // 🔙 GUARANTEED FIX: Simple method that always adds a visible back button
    private fun addVisibleBackButton() {
        try {
            // Create a prominent back button
            val backButton = Button(this).apply {
                text = "← Back To Scan"
                textSize = 18f
                setBackgroundColor(Color.parseColor("#4CAF50")) // Green color to stand out
                setTextColor(Color.WHITE)
                setPadding(40, 24, 40, 24)
                setOnClickListener {
                    Log.d("ResultsActivity", "Back button clicked - returning to Live View")
                    finish()
                }
            }

            // Create layout parameters with margins
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(24, 24, 24, 16) // Left, Top, Right, Bottom margins
            }

            // Add the back button as the very first item in the results container
            resultsContainer.addView(backButton, 0, layoutParams)

            Log.d("ResultsActivity", "✅ Visible back button added successfully")

        } catch (e: Exception) {
            Log.e("ResultsActivity", "❌ Failed to add visible back button", e)

            // Ultimate fallback: Show a toast with instructions
            Toast.makeText(this, "Use device back button to return to Live View", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadResults() {
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(this@ResultsActivity)
            val dao = db.barcodeDao()
            val allBarcodes = withContext(Dispatchers.IO) { dao.getAllBarcodes() }

            val grouped = allBarcodes
                .groupBy { it.sessionId }
                .mapValues { (_, barcodes) -> barcodes.distinctBy { it.value } }
                .toSortedMap(reverseOrder())

            withContext(Dispatchers.Main) {
                resultsContainer.removeAllViews()

                // 🔙 GUARANTEED FIX: Re-add back button first after clearing views
                addVisibleBackButton()

                val inflater = LayoutInflater.from(this@ResultsActivity)

                for ((sessionId, barcodes) in grouped) {
                    val cardView = inflater.inflate(R.layout.item_session_card, resultsContainer, false) as CardView
                    val titleText = cardView.findViewById<TextView>(R.id.sessionTitle)
                    val barcodeList = cardView.findViewById<LinearLayout>(R.id.barcodeList)

                    titleText.text = "SCAN CYCLE (${formatSessionTimestamp(sessionId)})"

                    val toteIds = mutableListOf<String>()
                    val olpns = mutableListOf<String>()

                    barcodes.forEach {
                        when (getBarcodeFormatName(it.format)) {
                            "Data Matrix", "QR Code", "EAN-13", "UPC-A", "ITF", "Code 39", "Code 93" -> toteIds.add(it.value)
                            "Code 128", "1D barcode" -> olpns.add(it.value)
                        }
                    }

                    if (toteIds.isNotEmpty() || olpns.isNotEmpty()) {
                        val heading = TextView(this@ResultsActivity).apply {
                            text = "Associated ToteId & OLPN"
                            textSize = 16f
                            setPadding(16)
                            setTypeface(null, android.graphics.Typeface.BOLD)
                        }
                        barcodeList.addView(heading)
                    }

                    val maxSize = maxOf(toteIds.size, olpns.size)
                    for (i in 0 until maxSize) {
                        val idOlpn = if (i < olpns.size) olpns[i] else "N/A"
                        val idTote = if (i < toteIds.size) toteIds[i] else "N/A"

                        val numberedText = TextView(this@ResultsActivity).apply {
                            text = "${i + 1}. "
                            textSize = 16f
                            setPadding(16, 8, 16, 0)
                            setTypeface(null, android.graphics.Typeface.BOLD)
                        }

                        val detailText = TextView(this@ResultsActivity).apply {
                            text = buildString {
                                append("  ")
                                append("ID OLPN: ")
                                append(idOlpn)
                                append("\n  ")
                                append("Tote ID: ")
                                append(idTote)
                            }
                            textSize = 16f
                            setPadding(32, 0, 16, 16)
                        }

                        barcodeList.addView(numberedText)
                        barcodeList.addView(detailText)
                    }

                    // ✅ Add "Captured Barcodes Data" button inside the correct scan card
                    if (sessionId == currentSessionId && !boxedImagePath.isNullOrEmpty()) {
                        val showImageButton = Button(this@ResultsActivity).apply {
                            text = "📸 Captured Barcodes Data"
                            setBackgroundColor(Color.parseColor("#009688"))
                            setTextColor(Color.WHITE)
                        }

                        showImageButton.setOnClickListener {
                            val imageView = ImageView(this@ResultsActivity)
                            imageView.setImageBitmap(BitmapFactory.decodeFile(boxedImagePath))

                            AlertDialog.Builder(this@ResultsActivity)
                                .setTitle("Detected Barcodes")
                                .setView(imageView)
                                .setPositiveButton("OK", null)
                                .show()
                        }

                        barcodeList.addView(showImageButton)
                    }

                    resultsContainer.addView(cardView)
                }
            }
        }
    }

    private fun showSubmitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Submit Data")
            .setMessage("Confirm Again for Submit Data")
            .setPositiveButton("Submit Now") { _, _ ->
                submitDataUsingNewWorkflow()
            }
            .setNegativeButton("Cancel", null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }

    private fun submitDataUsingNewWorkflow() {
        lifecycleScope.launch {
            try {
                val progressDialog = ProgressDialog(this@ResultsActivity).apply {
                    setTitle("Submitting Data")
                    setMessage("Executing workflow...")
                    setCancelable(false)
                    show()
                }

                val db = AppDatabase.getInstance(this@ResultsActivity)
                val dao = db.barcodeDao()

                val barcodes = withContext(Dispatchers.IO) {
                    if (currentSessionId != 0L) dao.getBarcodesBySession(currentSessionId)
                    else dao.getAllBarcodes()
                }

                val toteIds = mutableListOf<String>()
                val olpns = mutableListOf<String>()

                barcodes.forEach { barcode ->
                    when (getBarcodeFormatName(barcode.format)) {
                        "Data Matrix", "QR Code", "EAN-13", "UPC-A", "ITF", "Code 39", "Code 93" -> toteIds.add(barcode.value)
                        "Code 128", "1D barcode" -> olpns.add(barcode.value)
                    }
                }

                if (toteIds.isEmpty() || olpns.isEmpty()) {
                    progressDialog.dismiss()
                    showErrorDialog("No valid ToteId-OLPN pairs found. Need both Data Matrix/QR Code (ToteIDs) and 1D barcodes (OLPNs).")
                    return@launch
                }

                Log.d("ResultsActivity", "ToteIDs=${toteIds.size}, OLPNs=${olpns.size}")

                // APIs 1–3
                progressDialog.setMessage("Running (End Trips)...")
                withContext(Dispatchers.IO) {
                    chorusApiService.clearWorkflowData()
                }

                val endTripsResult = withContext(Dispatchers.IO) {
                    chorusApiService.executeEndAllTripsWorkflow(toteIds)
                }

                if (endTripsResult.isFailure) {
                    progressDialog.dismiss()
                    showErrorDialog("APIs End Trip failed: ${endTripsResult.exceptionOrNull()?.message}")
                    return@launch
                }

                // APIs 4–6
                progressDialog.setMessage("Running (Create → Track → Update)...")

                val submitResult = withContext(Dispatchers.IO) {
                    chorusApiService.executeCreateTrackUpdateWorkflow(
                        liveOLPNs = olpns,
                        liveToteIds = toteIds,
                        livePairOLPNs = olpns
                    )
                }

                progressDialog.dismiss()

                if (submitResult.isSuccess) {
                    // 📤 FIX 5: Show success dialog and return to main screen
                    showDetailedSuccessDialog(submitResult.getOrNull() ?: "All APIs completed successfully (1-6)")
                } else {
                    // 📤 FIX 5: Show failure dialog with retry/main page options
                    showErrorDialog("APIs 4-6 failed: ${submitResult.exceptionOrNull()?.message}")
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showErrorDialog("Submission failed: ${e.message}")
                }
            }
        }
    }

    // 📤 FIX 5: Enhanced success dialog that returns to main screen
    private fun showDetailedSuccessDialog(message: String) {
        val textView = TextView(this).apply {
            text = message
            textSize = 12f
            setPadding(32, 32, 32, 32)
            setTextIsSelectable(true)
        }

        AlertDialog.Builder(this)
            .setTitle("Workflow Execution Results ")
            .setView(textView)
            .setPositiveButton("Return to Main") { _, _ ->
                // Return to main screen (DashboardActivity)
                returnToMainScreen()
            }
            .setNeutralButton("Copy Results") { _, _ ->
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = android.content.ClipData.newPlainText("Workflow Results", message)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "Results copied to clipboard", Toast.LENGTH_SHORT).show()

                // Return to main screen after copying
                returnToMainScreen()
            }
            .setCancelable(false) // Force user to choose an option
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }

    // 📤 FIX 5: Enhanced error dialog with retry/main page options
    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Submission Failed")
            .setMessage(message)
            .setPositiveButton("Retry") { _, _ ->
                showSubmitConfirmationDialog()
            }
            .setNegativeButton("Main Page") { _, _ ->
                returnToMainScreen()
            }
            .setNeutralButton("Stay Here") { _, _ ->
                // Do nothing, stay on results screen
            }
            .setCancelable(false) // Force user to choose an option
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    // 📤 FIX 5: Return to main screen (DashboardActivity)
    private fun returnToMainScreen() {
        try {
            // Try to find the DashboardActivity class - adjust package if needed
            val dashboardClass = try {
                Class.forName("com.example.barcodescanner.ui.DashboardActivity")
            } catch (e: ClassNotFoundException) {
                // Fallback to standard finish if DashboardActivity not found
                Log.w("ResultsActivity", "DashboardActivity not found, using finish()")
                finish()
                return
            }

            val intent = Intent(this, dashboardClass).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e("ResultsActivity", "Failed to return to main screen", e)
            // Fallback: just finish this activity
            finish()
        }
    }

    private fun formatSessionTimestamp(timestamp: Long): String {
        return try {
            val formatter = SimpleDateFormat("MMM dd, yyyy, HH:mm", Locale.getDefault())
            formatter.format(Date(timestamp))
        } catch (e: Exception) {
            "Invalid"
        }
    }

    private fun getBarcodeFormatName(format: Int): String {
        return when (format) {
            1 -> "Code 128"
            2 -> "Code 39"
            4 -> "Code 93"
            8 -> "Codabar"
            16 -> "Data Matrix"
            32 -> "EAN-13"
            64 -> "EAN-8"
            128 -> "QR Code"
            256 -> "UPC-A"
            512 -> "UPC-E"
            1024 -> "PDF417"
            2048 -> "Aztec"
            4096 -> "ITF"
            else -> "1D barcode"
        }
    }

    // 🔙 FIX 1: Ensure proper back button handling
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                Log.d("ResultsActivity", "Back button pressed")
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        Log.d("ResultsActivity", "Hardware back button pressed")
        // Allow normal back button behavior
        super.onBackPressed()
    }

    // 🔙 FIX 1: Handle support navigate up
    override fun onSupportNavigateUp(): Boolean {
        Log.d("ResultsActivity", "Support navigate up pressed")
        finish()
        return true
    }
}