package com.example.barcodescanner.adapter;

import java.lang.System;

/**
 * RecyclerView adapter for displaying ToteID-OLPN associations
 * with sync status and retry functionality
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0002\u001a\u001bB-\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00020\rJ\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\nJ\b\u0010\u000f\u001a\u00020\rH\u0016J\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\nJ\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\nJ\u001c\u0010\u0012\u001a\u00020\u00062\n\u0010\u0013\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\f\u001a\u00020\rH\u0016J\u001c\u0010\u0014\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\rH\u0016J\u0014\u0010\u0018\u001a\u00020\u00062\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\nR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/example/barcodescanner/adapter/AssociationAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/barcodescanner/adapter/AssociationAdapter$AssociationViewHolder;", "onItemClick", "Lkotlin/Function1;", "Lcom/example/barcodescanner/data/local/AssociationEntity;", "", "onRetryClick", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "associations", "", "getAssociationAt", "position", "", "getErrorAssociations", "getItemCount", "getPendingAssociations", "getSubmittedAssociations", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "updateAssociations", "newAssociations", "AssociationDiffCallback", "AssociationViewHolder", "app_debug"})
public final class AssociationAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.barcodescanner.adapter.AssociationAdapter.AssociationViewHolder> {
    private final kotlin.jvm.functions.Function1<com.example.barcodescanner.data.local.AssociationEntity, kotlin.Unit> onItemClick = null;
    private final kotlin.jvm.functions.Function1<com.example.barcodescanner.data.local.AssociationEntity, kotlin.Unit> onRetryClick = null;
    private java.util.List<com.example.barcodescanner.data.local.AssociationEntity> associations;
    
    public AssociationAdapter(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.example.barcodescanner.data.local.AssociationEntity, kotlin.Unit> onItemClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.example.barcodescanner.data.local.AssociationEntity, kotlin.Unit> onRetryClick) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public com.example.barcodescanner.adapter.AssociationAdapter.AssociationViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.example.barcodescanner.adapter.AssociationAdapter.AssociationViewHolder holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    /**
     * Update associations list with DiffUtil for efficient updates
     */
    public final void updateAssociations(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.barcodescanner.data.local.AssociationEntity> newAssociations) {
    }
    
    /**
     * Get association at position
     */
    @org.jetbrains.annotations.Nullable
    public final com.example.barcodescanner.data.local.AssociationEntity getAssociationAt(int position) {
        return null;
    }
    
    /**
     * Get all submitted associations
     */
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.barcodescanner.data.local.AssociationEntity> getSubmittedAssociations() {
        return null;
    }
    
    /**
     * Get all pending associations
     */
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.barcodescanner.data.local.AssociationEntity> getPendingAssociations() {
        return null;
    }
    
    /**
     * Get associations with errors
     */
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.barcodescanner.data.local.AssociationEntity> getErrorAssociations() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\bR\u0011\u0010\u0012\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\bR\u0011\u0010\u0014\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\b\u00a8\u0006\u0016"}, d2 = {"Lcom/example/barcodescanner/adapter/AssociationAdapter$AssociationViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Lcom/example/barcodescanner/adapter/AssociationAdapter;Landroid/view/View;)V", "olpnTextView", "Landroid/widget/TextView;", "getOlpnTextView", "()Landroid/widget/TextView;", "retryButton", "Landroid/widget/Button;", "getRetryButton", "()Landroid/widget/Button;", "statusIndicator", "getStatusIndicator", "()Landroid/view/View;", "statusTextView", "getStatusTextView", "timestampTextView", "getTimestampTextView", "toteIdTextView", "getToteIdTextView", "app_debug"})
    public final class AssociationViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView toteIdTextView = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView olpnTextView = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView timestampTextView = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView statusTextView = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.Button retryButton = null;
        @org.jetbrains.annotations.NotNull
        private final android.view.View statusIndicator = null;
        
        public AssociationViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View view) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getToteIdTextView() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getOlpnTextView() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getTimestampTextView() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getStatusTextView() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.Button getRetryButton() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.view.View getStatusIndicator() {
            return null;
        }
    }
    
    /**
     * DiffUtil callback for efficient list updates
     */
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\b\u0010\u000f\u001a\u00020\nH\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/example/barcodescanner/adapter/AssociationAdapter$AssociationDiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$Callback;", "oldList", "", "Lcom/example/barcodescanner/data/local/AssociationEntity;", "newList", "(Ljava/util/List;Ljava/util/List;)V", "areContentsTheSame", "", "oldItemPosition", "", "newItemPosition", "areItemsTheSame", "getChangePayload", "", "getNewListSize", "getOldListSize", "app_debug"})
    static final class AssociationDiffCallback extends androidx.recyclerview.widget.DiffUtil.Callback {
        private final java.util.List<com.example.barcodescanner.data.local.AssociationEntity> oldList = null;
        private final java.util.List<com.example.barcodescanner.data.local.AssociationEntity> newList = null;
        
        public AssociationDiffCallback(@org.jetbrains.annotations.NotNull
        java.util.List<com.example.barcodescanner.data.local.AssociationEntity> oldList, @org.jetbrains.annotations.NotNull
        java.util.List<com.example.barcodescanner.data.local.AssociationEntity> newList) {
            super();
        }
        
        @java.lang.Override
        public int getOldListSize() {
            return 0;
        }
        
        @java.lang.Override
        public int getNewListSize() {
            return 0;
        }
        
        @java.lang.Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return false;
        }
        
        @java.lang.Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        @java.lang.Override
        public java.lang.Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return null;
        }
    }
}