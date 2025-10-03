package com.example.barcodescanner.ui;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\bH\u0002J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0010H\u0002J\b\u0010\u0017\u001a\u00020\u0010H\u0016J\u0012\u0010\u0018\u001a\u00020\u00102\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\u0010\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u000eH\u0016J\b\u0010\u001f\u001a\u00020\u0010H\u0002J\u0010\u0010 \u001a\u00020\u00102\u0006\u0010!\u001a\u00020\u0004H\u0002J\u0010\u0010\"\u001a\u00020\u00102\u0006\u0010!\u001a\u00020\u0004H\u0002J\b\u0010#\u001a\u00020\u0010H\u0002J\b\u0010$\u001a\u00020\u0010H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/example/barcodescanner/ui/ResultsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "boxedImagePath", "", "chorusApiService", "Lcom/example/barcodescanner/data/remote/ChorusApiService;", "currentSessionId", "", "doneButton", "Landroid/widget/Button;", "resultsContainer", "Landroid/widget/LinearLayout;", "scanCompleted", "", "addVisibleBackButton", "", "formatSessionTimestamp", "timestamp", "getBarcodeFormatName", "format", "", "loadResults", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onSupportNavigateUp", "returnToMainScreen", "showDetailedSuccessDialog", "message", "showErrorDialog", "showSubmitConfirmationDialog", "submitDataUsingNewWorkflow", "app_debug"})
public final class ResultsActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.LinearLayout resultsContainer;
    private android.widget.Button doneButton;
    private long currentSessionId = 0L;
    private boolean scanCompleted = false;
    private com.example.barcodescanner.data.remote.ChorusApiService chorusApiService;
    private java.lang.String boxedImagePath;
    
    public ResultsActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void addVisibleBackButton() {
    }
    
    private final void loadResults() {
    }
    
    private final void showSubmitConfirmationDialog() {
    }
    
    private final void submitDataUsingNewWorkflow() {
    }
    
    private final void showDetailedSuccessDialog(java.lang.String message) {
    }
    
    private final void showErrorDialog(java.lang.String message) {
    }
    
    private final void returnToMainScreen() {
    }
    
    private final java.lang.String formatSessionTimestamp(long timestamp) {
        return null;
    }
    
    private final java.lang.String getBarcodeFormatName(int format) {
        return null;
    }
    
    @java.lang.Override
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull
    android.view.MenuItem item) {
        return false;
    }
    
    @java.lang.Override
    public void onBackPressed() {
    }
    
    @java.lang.Override
    public boolean onSupportNavigateUp() {
        return false;
    }
}