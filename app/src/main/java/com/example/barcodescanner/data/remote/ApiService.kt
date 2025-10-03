package com.example.barcodescanner.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    // ===========================
    // NEW API ENDPOINTS (Based on Python code requirements)
    // ===========================

    /**
     * API #1: List All Trips in IN_TRANSIT
     * POST /v1alpha1/trips:list
     * Payload: { "tripStages": ["IN_TRANSIT"], "assetIdentifier": { "customerId": "<toteid>" } }
     */
    @POST("v1alpha1/trips:list")
    suspend fun listTripsInTransit(
        @Header("Authorization") authorization: String,
        @Body payload: ListTripsInTransitPayload
    ): Response<ListTripsResponse>

    /**
     * API #2 & #6: Update Trip Stage (COMPLETED or IN_TRANSIT)
     * POST /v1alpha1/trips:updateStage
     * Payload: { "tripIdentifier": { "customerId": "<OLPN>" }, "newStage": "COMPLETED" | "IN_TRANSIT" }
     */
    @POST("v1alpha1/trips:updateStage")
    suspend fun updateTripStage(
        @Header("Authorization") authorization: String,
        @Body payload: TripUpdateStagePayload
    ): Response<Any>

    /**
     * API #3: End Tracking - NEW ENDPOINT
     * POST /v1alpha1/trackings:end
     * Payload: { "assetIdentifier": { "customerId": "<toteid>" }, "tripIdentifier": { "customerId": "<OLPN>" } }
     */
    @POST("v1alpha1/trackings:end")
    suspend fun endTracking(
        @Header("Authorization") authorization: String,
        @Body payload: EndTrackingPayload
    ): Response<Any>

    /**
     * API #4: Create Trip
     * POST /v1alpha1/trips
     * Payload: { "trip": { "customer_id": "<OLPN>" } }
     */
    @POST("v1alpha1/trips")
    suspend fun createTrip(
        @Header("Authorization") authorization: String,
        @Body payload: CreateTripPayload
    ): Response<Any>

    /**
     * API #5: Add Trip to Tracking
     * POST /v1alpha1/trackings:addTrip
     * Payload: { "assetIdentifier": { "customerId": "<ToteId>" }, "tripIdentifier": { "customerId": "<OLPN>" } }
     */
    @POST("v1alpha1/trackings:addTrip")
    suspend fun addTripTracking(
        @Header("Authorization") authorization: String,
        @Body payload: AddTripTrackingPayload
    ): Response<Any>

    // ===========================
    // LEGACY ENDPOINTS (Keep for backward compatibility)
    // ===========================

    /**
     * Legacy: List trips by deviceId
     * @Deprecated Use listTripsInTransit instead
     */
    @POST("v1alpha1/trips:list")
    suspend fun listTrips(
        @Header("Authorization") authorization: String,
        @Body payload: ListTripsPayload
    ): Response<ListTripsResponse>

    /**
     * Legacy: Update trip stage (alternative endpoint)
     * @Deprecated Use updateTripStage instead
     */
    @POST("v1alpha1/trips:updateStage")
    suspend fun updateTripStageNew(
        @Header("Authorization") authorization: String,
        @Body payload: TripUpdateStagePayload
    ): Response<Any>

    /**
     * Legacy: Create trip (alternative)
     * @Deprecated Use createTrip instead
     */
    @POST("v1alpha1/trips")
    suspend fun createTripNew(
        @Header("Authorization") authorization: String,
        @Body payload: TripPayload
    ): Response<Any>

    /**
     * Legacy: Start tracking (alternative)
     * @Deprecated Use addTripTracking instead
     */
    @POST("v1alpha1/trackings:start")
    suspend fun startTrackingNew(
        @Header("Authorization") authorization: String,
        @Body payload: TrackingStartPayload
    ): Response<Any>

    // ===========================
    // ORIGINAL LEGACY ENDPOINTS (Keep for full backward compatibility)
    // ===========================

    @POST("v1alpha1/assets")
    suspend fun createAsset(
        @Header("Authorization") authorization: String,
        @Body payload: AssetPayload
    ): Response<Any>

    @POST("v1alpha1/trackings:start")
    suspend fun startTracking(
        @Header("Authorization") authorization: String,
        @Body payload: TrackingStartPayload
    ): Response<Any>

    // ===========================
    // LEGACY BARCODE ENDPOINTS (For ScanViewModel.kt compatibility)
    // ===========================

    /**
     * Legacy: Post barcode data
     * @Deprecated This endpoint is kept for backward compatibility only
     */
    @POST("v1alpha1/barcodes")
    suspend fun postBarcode(
        @Header("Authorization") authorization: String,
        @Body payload: BarcodePayload
    ): Response<Any>
}