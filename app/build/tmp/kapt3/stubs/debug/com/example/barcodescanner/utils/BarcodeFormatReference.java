package com.example.barcodescanner.utils;

import java.lang.System;

/**
 * Barcode Format Reference and Utility Functions
 *
 * 🔄 FIXED: Now uses correct MLKit barcode format constants
 * ✅ QR Code detection now works properly for ToteIDs
 *
 * This file provides centralized barcode format definitions and utility functions
 * for consistent barcode type detection across the application.
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\"\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0004J\u0016\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u001cJ\u0015\u0010 \u001a\u0004\u0018\u00010\u00042\u0006\u0010!\u001a\u00020\u001c\u00a2\u0006\u0002\u0010\"J\u000e\u0010#\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0004J\u000e\u0010$\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0004J\u000e\u0010%\u001a\u00020&2\u0006\u0010\u001d\u001a\u00020\u0004J\u000e\u0010\'\u001a\u00020&2\u0006\u0010\u001d\u001a\u00020\u0004J\u000e\u0010(\u001a\u00020&2\u0006\u0010\u001d\u001a\u00020\u0004J\u000e\u0010)\u001a\u00020&2\u0006\u0010\u001d\u001a\u00020\u0004J\u000e\u0010*\u001a\u00020&2\u0006\u0010\u001d\u001a\u00020\u0004J\u0006\u0010+\u001a\u00020,J\u0006\u0010-\u001a\u00020&R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2 = {"Lcom/example/barcodescanner/utils/BarcodeFormatReference;", "", "()V", "AZTEC", "", "CODABAR", "CODE_128", "CODE_39", "CODE_93", "DATA_MATRIX", "EAN_13", "EAN_8", "ITF", "OLPN_FORMATS", "", "getOLPN_FORMATS", "()Ljava/util/Set;", "OTHER_1D_FORMATS", "getOTHER_1D_FORMATS", "OTHER_2D_FORMATS", "getOTHER_2D_FORMATS", "PDF417", "QR_CODE", "TOTE_ID_FORMATS", "getTOTE_ID_FORMATS", "UPC_A", "UPC_E", "getBarcodeCategory", "", "format", "getBarcodeInfo", "value", "getFormatConstant", "formatName", "(Ljava/lang/String;)Ljava/lang/Integer;", "getFormatName", "getValidationMessage", "is1DFormat", "", "is2DFormat", "isOlpnFormat", "isToteIdFormat", "isValidForWorkflow", "printFormatMappings", "", "verifyMLKitCompatibility", "app_debug"})
public final class BarcodeFormatReference {
    @org.jetbrains.annotations.NotNull
    public static final com.example.barcodescanner.utils.BarcodeFormatReference INSTANCE = null;
    public static final int CODE_128 = 1;
    public static final int CODE_39 = 2;
    public static final int CODE_93 = 4;
    public static final int CODABAR = 8;
    public static final int DATA_MATRIX = 16;
    public static final int EAN_13 = 32;
    public static final int EAN_8 = 64;
    public static final int QR_CODE = 256;
    public static final int UPC_A = 512;
    public static final int UPC_E = 1024;
    public static final int PDF417 = 2048;
    public static final int AZTEC = 4096;
    public static final int ITF = 8192;
    
    /**
     * 🔄 FIXED: ToteID formats now include QR Code with correct constant
     */
    @org.jetbrains.annotations.NotNull
    private static final java.util.Set<java.lang.Integer> TOTE_ID_FORMATS = null;
    
    /**
     * OLPN formats (1D barcodes)
     */
    @org.jetbrains.annotations.NotNull
    private static final java.util.Set<java.lang.Integer> OLPN_FORMATS = null;
    
    /**
     * 2D formats not used for ToteID/OLPN classification
     */
    @org.jetbrains.annotations.NotNull
    private static final java.util.Set<java.lang.Integer> OTHER_2D_FORMATS = null;
    
    /**
     * Other 1D formats not used for OLPN classification
     */
    @org.jetbrains.annotations.NotNull
    private static final java.util.Set<java.lang.Integer> OTHER_1D_FORMATS = null;
    
    private BarcodeFormatReference() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Set<java.lang.Integer> getTOTE_ID_FORMATS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Set<java.lang.Integer> getOLPN_FORMATS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Set<java.lang.Integer> getOTHER_2D_FORMATS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Set<java.lang.Integer> getOTHER_1D_FORMATS() {
        return null;
    }
    
    /**
     * Get human-readable format name
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFormatName(int format) {
        return null;
    }
    
    /**
     * Check if barcode format is a ToteID
     * ✅ FIXED: Now correctly identifies QR codes as ToteIDs
     */
    public final boolean isToteIdFormat(int format) {
        return false;
    }
    
    /**
     * Check if barcode format is an OLPN
     * ✅ Enhanced: Better 1D barcode detection for OLPNs
     */
    public final boolean isOlpnFormat(int format) {
        return false;
    }
    
    /**
     * Check if barcode format is a 2D barcode
     */
    public final boolean is2DFormat(int format) {
        return false;
    }
    
    /**
     * Check if barcode format is a 1D barcode
     */
    public final boolean is1DFormat(int format) {
        return false;
    }
    
    /**
     * Get barcode category for logging/debugging
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getBarcodeCategory(int format) {
        return null;
    }
    
    /**
     * Get detailed barcode information
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getBarcodeInfo(int format, @org.jetbrains.annotations.NotNull
    java.lang.String value) {
        return null;
    }
    
    /**
     * Validate if a barcode can be processed in our workflow
     */
    public final boolean isValidForWorkflow(int format) {
        return false;
    }
    
    /**
     * Get validation message for unsupported formats
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getValidationMessage(int format) {
        return null;
    }
    
    /**
     * Print all barcode format mappings (for debugging)
     */
    public final void printFormatMappings() {
    }
    
    /**
     * ✅ NEW: Verify format constants match MLKit
     */
    public final boolean verifyMLKitCompatibility() {
        return false;
    }
    
    /**
     * ✅ NEW: Get format constant by name (useful for debugging)
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getFormatConstant(@org.jetbrains.annotations.NotNull
    java.lang.String formatName) {
        return null;
    }
}