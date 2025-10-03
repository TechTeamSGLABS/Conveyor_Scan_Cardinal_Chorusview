package com.example.barcodescanner.data.remote

import android.util.Log
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Base64
import com.google.gson.JsonParser
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import java.nio.charset.StandardCharsets
import com.google.gson.Gson
import java.util.*

class ChorusApiService {

    companion object {
        private const val API_DOMAIN = "api.chorussystems.net"
        private const val BASE_URL = "https://$API_DOMAIN/"
        private const val TAG = "ChorusApiService"

        // Service Account Configuration
        private const val PROJECT_ID = "glossy-handler-460608-c7"
        private const val CLIENT_EMAIL = "chorus-api-service-account@glossy-handler-460608-c7.iam.gserviceaccount.com"
        private const val CLIENT_ID = "110114679609486566127"
        private const val PRIVATE_KEY_ID = "1a7ad281a7efbe578371ab7336cf97c9fc520254"

        // 🔐 SECURITY WARNING: Store this securely in production!
        // Consider using Android Keystore, encrypted storage, or environment variables
        private const val PRIVATE_KEY = """-----BEGIN PRIVATE KEY-----
MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCe/aQsgg1fbf1Y
mM1fuyjPOTzyBZclOb8OcFmxJ+KaUk4yHEbUpJXVrIYmbECRYLu8crFx43DgwLfo
EJ3BKS5Q3LfROkDIbPxqp7ORcrD/DlV3PCqiIUP+1IFsdsBFHD1poO6F6MszXo08
TqQPtyh7vlSAtJxBBOqHRPmoeaNTx8CgUZhvENVGYG4IZon/J0XmFZoGUpx/Vltx
wXPTB3qybo7YdD3JWUq/oIupg8wc1xZjUV9drJTXW9T4ooMOTaQPvVrfBBJRfQdo
BMdj6ZfLd8BsTqor1h5hRdxrvwULXHtXyJ69VSL3yVP544O0Ci/PKrzIk8Uw31oA
T9avJgz9AgMBAAECggEABrmY++aislZomK/al1xx3mAKrKcMDLT2WKHrx/igAbc5
BlltV+RqpQ+Hhhuz7cz0a+/DAzD5DudVRWw3LAWLUhF1TKo6NXAXYNsylyn4LssD
APM1z3FORbq54GYJKHMzGCMd67fxfmesXDBsXpt4oNFSaYuaL2LfQ7T+cBVNvAb+
FMSquJTduz2GNAaREc9O56P9zHWKfODI8mNvIUxN56gtsVDQXi90EiCNMqeCnsg3
ZjTbZBlU5k7WCQtxj+OsbErHUMBZF7ngJ0IkMB6GPZFF1pskc0LyqsVeYHQ6q6N+
km6+rgs1xs+cJzHAgzFeGktmWWFIn+0TEf7v0pyKcQKBgQDewk4BwQFOqB+cGiXO
LzLfXio4mVVdZYNH68/GGQb1kXaYR3j6vQhVScaoSmkBXBZP+klAMlJHrDbQRMD4
AE/DlG1WmkwAugcPV+TLZZrFSpjhl5JTrrtc6ffbUqXoy/+4g1Qt6AJU1N3TfTAX
uPmM3nRBBaQTbrLq584y53eDTQKBgQC2t03wQE+SbdarcnrX5d4/ZRsLzp4GGxGy
lDMu8LelN083Y235POyDUxXwZ475DfTtjuEEqVXKtlTsdOKmMpwK25lRE8Q4J/G6
bD84AQsaze9TM8uzfFI9vYSF3dapRPkFDRT22gcuFdzr9hhZI4xTlPSyvrrW3wmU
g4fpxr14cQKBgBUlIpx+Aq7BvMZnoQESb+TOHnni7DyCX1TbaIyoYYW7+iL4Xd6H
RRw7dZUSAyey4xdSbdOfMzpEeauJazvfY7LwCvT2jpJfFj55nGlGSsvBigOYDLbO
1lA6kWGLVd5kRvDv3nTBGj+NOUVTE9aco2ugzi5B59JEZs1vlZ41ZJ3dAoGAeSpt
oNjE69Gy/dbY57sj+t5NZTMLT/k7bzUgDAzNe96FxbFGCHYCUcQTVG0YPsiml7Kc
Pb8diWQIRRXuB1CgcgLWVRHKmYLDpmCKO9VVS90sy/wZJVlYIGFBNJS7+Vwn1tWq
BVjoUgNlkYSM+O+96t9otfANbthRPg9LXhfRJcECgYEAy7V9NfdrFdjFPVJDgStB
Jo3G/zh9rcfbH23zRa/w1bX08v/RpIJsWE2GWdHXoWKx9RI63JYeg7qLpdk4+/DE
/T6Jzb4UQfbf3pb13lOuHvb4nqcdyavnP+rWD30VINRhOhJsLuU5HdXhKTcEywVi
rDoaka9mKMY/eBzktwkorrc=
-----END PRIVATE KEY-----"""

        // Token configuration
        private const val TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token"
        private const val SCOPE = "https://www.googleapis.com/auth/cloud-platform"

        // Token refresh buffer time (refresh 5 minutes before expiration)
        private const val REFRESH_BUFFER_TIME = 5 * 60 * 1000L

        // JWT algorithm and token validity
        private const val JWT_ALGORITHM = "RS256"
        private const val TOKEN_VALIDITY_SECONDS = 3600L // 1 hour
    }

    // Token management variables
    @Volatile
    private var currentToken: String? = null

    @Volatile
    private var tokenExpirationTime: Long = 0L

    @Volatile
    private var storedRefreshToken: String? = null

    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Store customerIds and tracking data for workflow continuity
    private var extractedCustomerIds: MutableList<String> = mutableListOf()
    private var extractedToteOlpnPairs: MutableList<Pair<String, String>> = mutableListOf()
    private var uniqueOLPNs: MutableSet<String> = mutableSetOf()
    private var toteOLPNPairs: MutableList<Pair<String, String>> = mutableListOf()

    // ===========================
    // JWT TOKEN GENERATION METHODS (Keep existing JWT methods)
    // ===========================

    /**
     * Create JWT header for service account authentication
     */
    private fun createJwtHeader(): String {
        val header = mapOf(
            "alg" to JWT_ALGORITHM,
            "typ" to "JWT",
            "kid" to PRIVATE_KEY_ID
        )
        val headerJson = Gson().toJson(header)
        val encoded = Base64.encodeToString(headerJson.toByteArray(StandardCharsets.UTF_8), Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
        Log.d(TAG, "JWT Header: $headerJson")
        Log.d(TAG, "Encoded Header: $encoded")
        return encoded
    }

    /**
     * Create JWT payload for service account authentication
     */
    private fun createJwtPayload(): String {
        val now = System.currentTimeMillis() / 1000
        val payload = mapOf(
            "iss" to CLIENT_EMAIL,
            "sub" to CLIENT_EMAIL,
            "aud" to BASE_URL,
            "iat" to now,
            "exp" to (now + TOKEN_VALIDITY_SECONDS)
        )
        val payloadJson = Gson().toJson(payload)
        val encoded = Base64.encodeToString(payloadJson.toByteArray(StandardCharsets.UTF_8), Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
        Log.d(TAG, "JWT Payload: $payloadJson")
        Log.d(TAG, "Encoded Payload: $encoded")
        return encoded
    }

    /**
     * Sign JWT using private key
     */
    private fun signJwt(data: String): String {
        return try {
            Log.d(TAG, "Signing data: $data")

            // Clean the private key - remove headers, footers, and all whitespace including newlines
            val privateKeyContent = PRIVATE_KEY
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("\\s+".toRegex(), "") // Remove ALL whitespace including newlines
                .trim()

            Log.d(TAG, "Cleaned private key length: ${privateKeyContent.length}")

            // Decode the private key
            val keyBytes = Base64.decode(privateKeyContent, Base64.DEFAULT)
            val keySpec = PKCS8EncodedKeySpec(keyBytes)
            val keyFactory = KeyFactory.getInstance("RSA")
            val privateKey = keyFactory.generatePrivate(keySpec)

            Log.d(TAG, "Private key loaded successfully")

            // Sign the data
            val signature = Signature.getInstance("SHA256withRSA")
            signature.initSign(privateKey)
            signature.update(data.toByteArray(StandardCharsets.UTF_8))
            val signatureBytes = signature.sign()

            val encoded = Base64.encodeToString(signatureBytes, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
            Log.d(TAG, "Signature generated successfully, length: ${encoded.length}")
            return encoded
        } catch (e: Exception) {
            Log.e(TAG, "Failed to sign JWT", e)
            throw e
        }
    }

    /**
     * Generate JWT token for service account authentication
     */
    private fun generateJwtToken(): String {
        return try {
            Log.d(TAG, "Generating JWT token...")

            val header = createJwtHeader()
            val payload = createJwtPayload()
            val unsignedToken = "$header.$payload"

            Log.d(TAG, "Unsigned token: $unsignedToken")

            val signature = signJwt(unsignedToken)
            val finalToken = "$unsignedToken.$signature"

            // Validate the token format
            val parts = finalToken.split(".")
            if (parts.size != 3) {
                throw Exception("Generated JWT has ${parts.size} parts instead of 3. Token: $finalToken")
            }

            Log.d(TAG, "JWT token generated successfully. Parts: ${parts.size}")
            Log.d(TAG, "Header part length: ${parts[0].length}")
            Log.d(TAG, "Payload part length: ${parts[1].length}")
            Log.d(TAG, "Signature part length: ${parts[2].length}")
            Log.d(TAG, "Complete JWT (first 50 chars): ${finalToken.take(50)}...")

            return finalToken
        } catch (e: Exception) {
            Log.e(TAG, "Failed to generate JWT token", e)
            throw e
        }
    }

    // ===========================
    // TOKEN MANAGEMENT METHODS (Keep existing token methods)
    // ===========================

    /**
     * Parse JWT token to extract expiration time
     */
    private fun parseTokenExpiration(token: String): Long {
        return try {
            val parts = token.split(".")
            if (parts.size >= 2) {
                val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
                val json = JsonParser.parseString(payload).asJsonObject
                val exp = json.get("exp")?.asLong ?: 0L
                exp * 1000L // Convert to milliseconds
            } else {
                0L
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse token expiration", e)
            0L
        }
    }

    /**
     * Check if the current token is expired or will expire soon (within buffer time)
     */
    private fun isTokenExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        return tokenExpirationTime <= (currentTime + REFRESH_BUFFER_TIME)
    }

    /**
     * Refresh the JWT token using service account
     */
    private suspend fun refreshToken(): Boolean {
        return try {
            Log.d(TAG, "Generating new JWT token using service account")

            // Generate new JWT token
            val newToken = generateJwtToken()
            currentToken = newToken
            tokenExpirationTime = System.currentTimeMillis() + (TOKEN_VALIDITY_SECONDS * 1000L)

            Log.d(TAG, "JWT token generated successfully. New expiration: ${Date(tokenExpirationTime)}")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Exception during JWT token generation", e)
            false
        }
    }

    /**
     * Get authorization header with auto-refresh capability
     */
    private suspend fun getAuthHeader(): String {
        // Check if token needs refresh or if we don't have a token
        if (currentToken == null || isTokenExpired()) {
            Log.d(TAG, "Token is expired or missing, generating new token")
            val refreshSuccess = refreshToken()
            if (!refreshSuccess) {
                Log.w(TAG, "Failed to generate new token")
                throw Exception("Failed to generate authentication token")
            }
        }

        return "Bearer $currentToken"
    }

    /**
     * Execute API call with automatic token refresh on 401/403 errors
     */
    private suspend fun <T> executeWithTokenRetry(apiCall: suspend (String) -> retrofit2.Response<T>): retrofit2.Response<T> {
        // First attempt with current token
        var authHeader = getAuthHeader()
        var response = apiCall(authHeader)

        // If we get 401/403, try to refresh token and retry once
        if (response.code() == 401 || response.code() == 403) {
            Log.d(TAG, "Received ${response.code()}, generating new token and retrying")

            val refreshSuccess = refreshToken()
            if (refreshSuccess) {
                authHeader = getAuthHeader()
                response = apiCall(authHeader)
                Log.d(TAG, "Retry with new token resulted in: ${response.code()}")
            } else {
                Log.e(TAG, "Token generation failed, cannot retry API call")
            }
        }

        return response
    }

    // ===========================
    // NEW API METHODS (Based on Python code requirements)
    // ===========================

    /**
     * API #1: List All Trips (Use toteId i.e 2D barcode)
     * Payload: { "tripStages": ["IN_TRANSIT"], "assetIdentifier": { "customerId": "<toteid>" } }
     * URL: /v1alpha1/trips:list
     */
    suspend fun listAllTripsInTransit(toteId: String): Result<List<String>> {
        return try {
            Log.d(TAG, "Listing all IN_TRANSIT trips for toteId: $toteId")

            val payload = ListTripsInTransitPayload(
                tripStages = listOf("IN_TRANSIT"),
                assetIdentifier = AssetIdentifier(customerId = toteId)
            )

            val response = executeWithTokenRetry { authHeader ->
                apiService.listTripsInTransit(authHeader, payload)
            }

            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d(TAG, "List trips response: $responseBody")

                // Extract customerIds and tote-OLPN pairs from the trips array
                val customerIds = mutableListOf<String>()
                val toteOlpnPairs = mutableListOf<Pair<String, String>>()

                responseBody?.trips?.forEach { tripInfo ->
                    tripInfo.customerId?.takeIf { it.isNotBlank() }?.let { customerId ->
                        customerIds.add(customerId)
                        // Store the pair of toteId and OLPN for later use in endTracking
                        toteOlpnPairs.add(Pair(toteId, customerId))
                    }
                }

                // Store extracted data for the next workflow steps
                extractedCustomerIds.clear()
                extractedCustomerIds.addAll(customerIds)

                extractedToteOlpnPairs.clear()
                extractedToteOlpnPairs.addAll(toteOlpnPairs)

                Log.d(TAG, "Extracted ${customerIds.size} customerIds from trips: $customerIds")
                Log.d(TAG, "Extracted ${toteOlpnPairs.size} tote-OLPN pairs: $toteOlpnPairs")

                // Display detailed trip information for debugging
                responseBody?.trips?.forEach { trip ->
                    Log.d(TAG, "Trip - customerId: ${trip.customerId}, status: ${trip.status}, stage: ${trip.stage}")
                }

                Result.success(customerIds)
            } else {
                val errorBody = response.errorBody()?.string() ?: "No error body"
                val error = "Failed to list trips for toteId $toteId: ${response.code()} - ${response.message()} | $errorBody"
                Log.e(TAG, error)
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception listing trips for toteId: $toteId", e)
            Result.failure(e)
        }
    }

    /**
     * API #2: End All Trips (Use customerId i.e OLPN from List All trips API #1)
     * Payload: { "tripIdentifier": { "customerId": "<OLPN>" }, "newStage": "COMPLETED" }
     * URL: /v1alpha1/trips:updateStage
     */
    suspend fun endAllTripsMarkCompleted(customerIds: List<String>? = null): Result<String> {
        return try {
            val idsToProcess = customerIds ?: extractedCustomerIds

            if (idsToProcess.isEmpty()) {
                return Result.failure(Exception("No customer IDs available to end trips. Run listAllTripsInTransit first or provide customerIds."))
            }

            Log.d(TAG, "Ending ${idsToProcess.size} trips using customerIds: $idsToProcess")
            val results = mutableListOf<String>()
            val errors = mutableListOf<String>()

            for (customerId in idsToProcess) {
                try {
                    val payload = TripUpdateStagePayload(
                        tripIdentifier = TripIdentifier(customerId = customerId),
                        newStage = "COMPLETED"
                    )

                    val response = executeWithTokenRetry { authHeader ->
                        apiService.updateTripStage(authHeader, payload)
                    }

                    if (response.isSuccessful) {
                        results.add("Successfully ended trip for customerId: $customerId")
                        Log.d(TAG, "Successfully ended trip for customerId: $customerId")
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "No error body"
                        val error = "Failed to end trip for customerId $customerId: ${response.code()} - ${response.message()} | $errorBody"
                        errors.add(error)
                        Log.e(TAG, error)
                    }

                    delay(200) // Small delay between requests to avoid overwhelming the server
                } catch (e: Exception) {
                    val error = "Exception ending trip for customerId $customerId: ${e.message}"
                    errors.add(error)
                    Log.e(TAG, error, e)
                }
            }

            if (errors.isEmpty()) {
                val summary = "Successfully ended ${results.size} trips"
                Log.d(TAG, summary)
                Result.success(summary)
            } else {
                val summary = "Ended ${results.size} trips successfully. ${errors.size} failed.\nSuccessful: ${results.joinToString(", ")}\nErrors: ${errors.joinToString("\n")}"
                Log.w(TAG, summary)
                Result.failure(Exception(summary))
            }

        } catch (e: Exception) {
            Log.e(TAG, "Exception in endAllTripsMarkCompleted", e)
            Result.failure(e)
        }
    }

    /**
     * API #3: End Tracking (Toteid and OLPN from list of all trips api #1) - NEW API
     * Payload: { "assetIdentifier": { "customerId": "<toteid>" }, "tripIdentifier": { "customerId": "<OLPN>" } }
     * URL: /v1alpha1/trackings:end
     */
    suspend fun endTrackingForPairs(toteOlpnPairs: List<Pair<String, String>>? = null): Result<String> {
        return try {
            val pairsToProcess = toteOlpnPairs ?: extractedToteOlpnPairs

            if (pairsToProcess.isEmpty()) {
                return Result.failure(Exception("No tote-OLPN pairs available to end tracking. Run listAllTripsInTransit first or provide pairs."))
            }

            Log.d(TAG, "Ending tracking for ${pairsToProcess.size} tote-OLPN pairs: $pairsToProcess")
            val results = mutableListOf<String>()
            val errors = mutableListOf<String>()

            for ((toteId, olpn) in pairsToProcess) {
                try {
                    val payload = EndTrackingPayload(
                        assetIdentifier = AssetIdentifier(customerId = toteId),
                        tripIdentifier = TripIdentifier(customerId = olpn)
                    )

                    val response = executeWithTokenRetry { authHeader ->
                        apiService.endTracking(authHeader, payload)
                    }

                    if (response.isSuccessful) {
                        results.add("Successfully ended tracking for ToteId: $toteId, OLPN: $olpn")
                        Log.d(TAG, "Successfully ended tracking for ToteId: $toteId, OLPN: $olpn")
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "No error body"
                        val error = "Failed to end tracking for ToteId $toteId/OLPN $olpn: ${response.code()} - ${response.message()} | $errorBody"
                        errors.add(error)
                        Log.e(TAG, error)
                    }

                    delay(250) // Small delay between requests
                } catch (e: Exception) {
                    val error = "Exception ending tracking for ToteId $toteId/OLPN $olpn: ${e.message}"
                    errors.add(error)
                    Log.e(TAG, error, e)
                }
            }

            if (errors.isEmpty()) {
                val summary = "Successfully ended tracking for ${results.size} pairs"
                Log.d(TAG, summary)
                Result.success(summary)
            } else {
                val summary = "Ended tracking for ${results.size} pairs successfully. ${errors.size} failed.\nSuccessful: ${results.joinToString(", ")}\nErrors: ${errors.joinToString("\n")}"
                Log.w(TAG, summary)
                Result.failure(Exception(summary))
            }

        } catch (e: Exception) {
            Log.e(TAG, "Exception in endTrackingForPairs", e)
            Result.failure(e)
        }
    }

    /**
     * API #4: Create a Trip (use Live Scanned data OLPN) - Eliminates duplicates
     * Payload: { "trip": { "customer_id": "<OLPN>" } }
     * URL: /v1alpha1/trips
     */
    suspend fun createTripsFromLiveOLPNs(liveOLPNs: List<String>): Result<String> {
        return try {
            // Eliminate duplicates and update internal unique set
            val newUniqueOLPNs = liveOLPNs.toSet() - uniqueOLPNs
            uniqueOLPNs.addAll(newUniqueOLPNs)

            if (newUniqueOLPNs.isEmpty()) {
                val message = "No new unique OLPNs to create trips for. All provided OLPNs already exist."
                Log.d(TAG, message)
                return Result.success(message)
            }

            Log.d(TAG, "Creating trips for ${newUniqueOLPNs.size} unique OLPNs: $newUniqueOLPNs")

            val results = mutableListOf<String>()
            val errors = mutableListOf<String>()

            for (olpn in newUniqueOLPNs) {
                try {
                    val payload = CreateTripPayload(
                        trip = TripData(customer_id = olpn) // Using customer_id field as per Python payload
                    )

                    val response = executeWithTokenRetry { authHeader ->
                        apiService.createTrip(authHeader, payload)
                    }

                    if (response.isSuccessful) {
                        results.add("Successfully created trip for OLPN: $olpn")
                        Log.d(TAG, "Successfully created trip for OLPN: $olpn")
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "No error body"
                        val error = "Failed to create trip for OLPN $olpn: ${response.code()} - ${response.message()} | $errorBody"
                        errors.add(error)
                        Log.e(TAG, error)
                    }

                    delay(300) // Delay between requests
                } catch (e: Exception) {
                    val error = "Exception creating trip for OLPN $olpn: ${e.message}"
                    errors.add(error)
                    Log.e(TAG, error, e)
                }
            }

            if (errors.isEmpty()) {
                val summary = "Successfully created ${results.size} trips from unique OLPNs"
                Log.d(TAG, summary)
                Result.success(summary)
            } else {
                val summary = "Created ${results.size} trips successfully. ${errors.size} failed.\nSuccessful: ${results.joinToString(", ")}\nErrors: ${errors.joinToString("\n")}"
                Log.w(TAG, summary)
                Result.failure(Exception(summary))
            }

        } catch (e: Exception) {
            Log.e(TAG, "Exception in createTripsFromLiveOLPNs", e)
            Result.failure(e)
        }
    }

    /**
     * API #5: Start tracking trip (toteid, OLPN) - Handles pairs data with unique validation
     * Payload: { "assetIdentifier": { "customerId": "<ToteId>" }, "tripIdentifier": { "customerId": "<OLPN>" } }
     * URL: /v1alpha1/trackings:addTrip
     */
    suspend fun startTrackingTripsWithPairs(toteIds: List<String>, olpns: List<String>): Result<String> {
        return try {
            // Create pairs and eliminate duplicates
            val newPairs = mutableListOf<Pair<String, String>>()
            val minSize = minOf(toteIds.size, olpns.size)

            for (i in 0 until minSize) {
                val pair = Pair(toteIds[i], olpns[i])
                if (!toteOLPNPairs.contains(pair)) {
                    newPairs.add(pair)
                    toteOLPNPairs.add(pair)
                }
            }

            if (newPairs.isEmpty()) {
                val message = "No new unique tote-OLPN pairs to start tracking. All pairs already exist."
                Log.d(TAG, message)
                return Result.success(message)
            }

            Log.d(TAG, "Starting tracking for ${newPairs.size} unique pairs: $newPairs")

            val results = mutableListOf<String>()
            val errors = mutableListOf<String>()

            for ((toteId, olpn) in newPairs) {
                try {
                    val payload = AddTripTrackingPayload(
                        assetIdentifier = AssetIdentifier(customerId = toteId),
                        tripIdentifier = TripIdentifier(customerId = olpn)
                    )

                    val response = executeWithTokenRetry { authHeader ->
                        apiService.addTripTracking(authHeader, payload)
                    }

                    if (response.isSuccessful) {
                        results.add("Successfully started tracking for ToteId: $toteId, OLPN: $olpn")
                        Log.d(TAG, "Successfully started tracking for ToteId: $toteId, OLPN: $olpn")
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "No error body"
                        val error = "Failed to start tracking for ToteId $toteId/OLPN $olpn: ${response.code()} - ${response.message()} | $errorBody"
                        errors.add(error)
                        Log.e(TAG, error)
                    }

                    delay(400) // Delay between requests
                } catch (e: Exception) {
                    val error = "Exception starting tracking for ToteId $toteId/OLPN $olpn: ${e.message}"
                    errors.add(error)
                    Log.e(TAG, error, e)
                }
            }

            if (errors.isEmpty()) {
                val summary = "Successfully started tracking for ${results.size} pairs"
                Log.d(TAG, summary)
                Result.success(summary)
            } else {
                val summary = "Started tracking for ${results.size} pairs successfully. ${errors.size} failed.\nSuccessful: ${results.joinToString(", ")}\nErrors: ${errors.joinToString("\n")}"
                Log.w(TAG, summary)
                Result.failure(Exception(summary))
            }

        } catch (e: Exception) {
            Log.e(TAG, "Exception in startTrackingTripsWithPairs", e)
            Result.failure(e)
        }
    }

    /**
     * API #6: Update trip (OLPN, IN_Transit) - All unique OLPNs should be marked as IN_TRANSIT
     * Payload: { "tripIdentifier": { "customerId": "<OLPN>" }, "newStage": "IN_TRANSIT" }
     * URL: /v1alpha1/trips:updateStage
     */
    suspend fun updateTripsToInTransit(olpns: List<String>? = null): Result<String> {
        return try {
            val olpnsToProcess = olpns?.toSet() ?: uniqueOLPNs

            if (olpnsToProcess.isEmpty()) {
                return Result.failure(Exception("No OLPNs available to update. Create trips first or provide OLPN list."))
            }

            Log.d(TAG, "Updating ${olpnsToProcess.size} trips to IN_TRANSIT for OLPNs: $olpnsToProcess")

            val results = mutableListOf<String>()
            val errors = mutableListOf<String>()

            for (olpn in olpnsToProcess) {
                try {
                    val payload = TripUpdateStagePayload(
                        tripIdentifier = TripIdentifier(customerId = olpn),
                        newStage = "IN_TRANSIT"
                    )

                    val response = executeWithTokenRetry { authHeader ->
                        apiService.updateTripStage(authHeader, payload)
                    }

                    if (response.isSuccessful) {
                        results.add("Successfully updated trip to IN_TRANSIT for OLPN: $olpn")
                        Log.d(TAG, "Successfully updated trip to IN_TRANSIT for OLPN: $olpn")
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "No error body"
                        val error = "Failed to update trip to IN_TRANSIT for OLPN $olpn: ${response.code()} - ${response.message()} | $errorBody"
                        errors.add(error)
                        Log.e(TAG, error)
                    }

                    delay(250) // Delay between requests
                } catch (e: Exception) {
                    val error = "Exception updating trip to IN_TRANSIT for OLPN $olpn: ${e.message}"
                    errors.add(error)
                    Log.e(TAG, error, e)
                }
            }

            if (errors.isEmpty()) {
                val summary = "Successfully updated ${results.size} trips to IN_TRANSIT"
                Log.d(TAG, summary)
                Result.success(summary)
            } else {
                val summary = "Updated ${results.size} trips successfully. ${errors.size} failed.\nSuccessful: ${results.joinToString(", ")}\nErrors: ${errors.joinToString("\n")}"
                Log.w(TAG, summary)
                Result.failure(Exception(summary))
            }

        } catch (e: Exception) {
            Log.e(TAG, "Exception in updateTripsToInTransit", e)
            Result.failure(e)
        }
    }

    // ===========================
    // WORKFLOW EXECUTION METHODS
    // ===========================

    /**
     * Execute APIs 1-3: End All Trips Workflow (for "End All Trips" button)
     * This method processes all toteIds to list trips, end trips, and end tracking
     */
    suspend fun executeEndAllTripsWorkflow(toteIds: List<String>): Result<String> {
        return try {
            Log.d(TAG, "Starting End All Trips workflow (APIs 1-3) for ${toteIds.size} toteIds")
            val workflowResults = mutableListOf<String>()

            // Process each toteId one by one
            for ((index, toteId) in toteIds.withIndex()) {
                Log.d(TAG, "Processing toteId ${index + 1}/${toteIds.size}: $toteId")

                // API #1: List All Trips for this toteId
                Log.d(TAG, "API #1: Listing all IN_TRANSIT trips for toteId: $toteId")
                val listResult = listAllTripsInTransit(toteId)
                if (listResult.isFailure) {
                    workflowResults.add("ToteId $toteId - API #1 failed: ${listResult.exceptionOrNull()?.message}")
                    continue // Skip to next toteId
                }
                workflowResults.add("ToteId $toteId - API #1: Listed ${listResult.getOrNull()?.size ?: 0} trips")

                delay(1000)
            }

            // API #2: End All Trips using all collected customerIds
            Log.d(TAG, "API #2: Ending all trips")
            val endResult = endAllTripsMarkCompleted()
            if (endResult.isFailure) {
                workflowResults.add("API #2 failed: ${endResult.exceptionOrNull()?.message}")
            } else {
                workflowResults.add("API #2: ${endResult.getOrNull()}")
            }

            delay(1000)

            // API #3: End tracking for all collected tote-OLPN pairs
            Log.d(TAG, "API #3: Ending tracking for all pairs")
            val endTrackingResult = endTrackingForPairs()
            if (endTrackingResult.isFailure) {
                workflowResults.add("API #3 failed: ${endTrackingResult.exceptionOrNull()?.message}")
            } else {
                workflowResults.add("API #3: ${endTrackingResult.getOrNull()}")
            }

            Log.d(TAG, "End All Trips workflow (APIs 1-3) completed for ${toteIds.size} toteIds")
            Result.success("End All Trips workflow completed:\n${workflowResults.joinToString("\n")}")

        } catch (e: Exception) {
            Log.e(TAG, "Exception in End All Trips workflow", e)
            Result.failure(e)
        }
    }

    /**
     * Execute APIs 4-6: Create-Track-Update Workflow (for submit data)
     * This method processes live OLPN and ToteId data for the main workflow
     */
    suspend fun executeCreateTrackUpdateWorkflow(
        liveOLPNs: List<String>,     // Live captured OLPNs for creating trips
        liveToteIds: List<String>,   // Live captured ToteIds for tracking pairs
        livePairOLPNs: List<String>  // Live captured OLPNs for tracking pairs
    ): Result<String> {
        return try {
            Log.d(TAG, "Starting Create-Track-Update workflow (APIs 4-6)")
            val workflowResults = mutableListOf<String>()

            // API #4: Create Trips from Live OLPNs
            Log.d(TAG, "API #4: Creating trips from live OLPNs")
            val createResult = createTripsFromLiveOLPNs(liveOLPNs)
            if (createResult.isFailure) {
                return Result.failure(Exception("API #4 failed: ${createResult.exceptionOrNull()?.message}"))
            }
            workflowResults.add("API #4: ${createResult.getOrNull()}")

            delay(1000)

            // API #5: Start Tracking with Live Pairs
            Log.d(TAG, "API #5: Starting tracking for live pairs")
            val trackingResult = startTrackingTripsWithPairs(liveToteIds, livePairOLPNs)
            if (trackingResult.isFailure) {
                return Result.failure(Exception("API #5 failed: ${trackingResult.exceptionOrNull()?.message}"))
            }
            workflowResults.add("API #5: ${trackingResult.getOrNull()}")

            delay(1000)

            // API #6: Update Trips to IN_TRANSIT
            Log.d(TAG, "API #6: Updating trips to IN_TRANSIT")
            val updateResult = updateTripsToInTransit()
            if (updateResult.isFailure) {
                return Result.failure(Exception("API #6 failed: ${updateResult.exceptionOrNull()?.message}"))
            }
            workflowResults.add("API #6: ${updateResult.getOrNull()}")

            Log.d(TAG, "Create-Track-Update workflow (APIs 4-6) completed successfully")
            Result.success("Create-Track-Update workflow completed successfully:\n${workflowResults.joinToString("\n")}")

        } catch (e: Exception) {
            Log.e(TAG, "Exception in Create-Track-Update workflow", e)
            Result.failure(e)
        }
    }

    // ===========================
    // UTILITY METHODS
    // ===========================

    /**
     * Clear all cached data for fresh workflow execution
     */
    fun clearWorkflowData() {
        extractedCustomerIds.clear()
        extractedToteOlpnPairs.clear()
        uniqueOLPNs.clear()
        toteOLPNPairs.clear()
        Log.d(TAG, "Workflow data cleared")
    }

    /**
     * Get current workflow state for debugging
     */
    fun getWorkflowState(): Map<String, Any> {
        return mapOf(
            "extractedCustomerIds" to extractedCustomerIds.toList(),
            "extractedToteOlpnPairs" to extractedToteOlpnPairs.toList(),
            "uniqueOLPNs" to uniqueOLPNs.toList(),
            "toteOLPNPairs" to toteOLPNPairs.toList()
        )
    }

    /**
     * Get current token status for debugging
     */
    fun getTokenStatus(): Map<String, Any> {
        val currentTime = System.currentTimeMillis()
        val timeUntilExpiration = tokenExpirationTime - currentTime

        return mapOf(
            "hasToken" to (currentToken != null),
            "isExpired" to isTokenExpired(),
            "expirationTime" to Date(tokenExpirationTime).toString(),
            "timeUntilExpiration" to "${timeUntilExpiration / 1000 / 60} minutes",
            "tokenPreview" to (currentToken?.take(20) ?: "No token") + "...",
            "serviceAccount" to CLIENT_EMAIL,
            "projectId" to PROJECT_ID
        )
    }

    /**
     * Manually refresh token (for testing purposes)
     */
    suspend fun manualRefreshToken(): Result<String> {
        return try {
            val success = refreshToken()
            if (success) {
                Result.success("Token refreshed successfully. New expiration: ${Date(tokenExpirationTime)}")
            } else {
                Result.failure(Exception("Failed to refresh token"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ===========================
    // BACKWARD COMPATIBILITY METHODS (Keep existing legacy methods)
    // ===========================

    // Keep all existing legacy methods for backward compatibility
    // These can be marked as @Deprecated but should remain functional

    @Deprecated("Use executeEndAllTripsWorkflow instead", ReplaceWith("executeEndAllTripsWorkflow"))
    suspend fun listAllTrips(toteId: String): Result<List<String>> {
        // Map to new method for backward compatibility
        return listAllTripsInTransit(toteId)
    }

    @Deprecated("Use endAllTripsMarkCompleted instead", ReplaceWith("endAllTripsMarkCompleted"))
    suspend fun endAllTrips(customerIds: List<String>? = null): Result<String> {
        // Map to new method for backward compatibility
        return endAllTripsMarkCompleted(customerIds)
    }

    @Deprecated("Use createTripsFromLiveOLPNs instead", ReplaceWith("createTripsFromLiveOLPNs"))
    suspend fun createTripsFromOLPNs(liveOLPNs: List<String>): Result<String> {
        // Map to new method for backward compatibility
        return createTripsFromLiveOLPNs(liveOLPNs)
    }

    @Deprecated("Use startTrackingTripsWithPairs instead", ReplaceWith("startTrackingTripsWithPairs"))
    suspend fun startTrackingTrips(toteIds: List<String>, olpns: List<String>): Result<String> {
        // Map to new method for backward compatibility
        return startTrackingTripsWithPairs(toteIds, olpns)
    }

    // ===========================
    // LEGACY METHODS (KEPT FOR COMPATIBILITY)
    // ===========================

    /**
     * Legacy method - Submit data for a single tote-OLPN pair
     */
    suspend fun submitSinglePair(deviceId: String, toteId: String, olpn: String): Result<String> {
        return try {
            val customerId = createCustomerId(toteId, olpn)
            Log.d(TAG, "Starting submission for $toteId, $olpn using deviceId: $deviceId")

            // Step 1: Create Asset
            val assetResult = createAsset(customerId)
            if (!assetResult.isSuccess) {
                return assetResult
            }

            delay(500)

            // Step 2: Start Tracking with Asset
            val trackingResult = startTrackingWithAsset(deviceId, customerId)
            if (!trackingResult.isSuccess) {
                return trackingResult
            }

            delay(500)

            // Step 3: Create Trip
            val tripResult = createTrip(customerId)
            if (!tripResult.isSuccess) {
                return tripResult
            }

            delay(500)

            // Step 4: Update Trip Stage
            val stageResult = updateTripStage(customerId)
            if (!stageResult.isSuccess) {
                return stageResult
            }

            delay(500)

            // Step 5: Start Tracking with Trip
            val finalTrackingResult = startTrackingWithTrip(deviceId, customerId)
            if (!finalTrackingResult.isSuccess) {
                return finalTrackingResult
            }

            Log.d(TAG, "Successfully submitted data for ToteId: $toteId, OLPN: $olpn")
            Result.success("Successfully submitted ToteId: $toteId, OLPN: $olpn")

        } catch (e: Exception) {
            Log.e(TAG, "Failed to submit data for ToteId: $toteId, OLPN: $olpn", e)
            Result.failure(e)
        }
    }

    private fun createCustomerId(toteId: String, olpn: String): String {
        return "$toteId, $olpn"
    }

    private suspend fun createAsset(customerId: String): Result<String> {
        return try {
            val payload = AssetPayload(
                asset = Asset(customerId = customerId)
            )

            val response = executeWithTokenRetry { authHeader ->
                apiService.createAsset(authHeader, payload)
            }

            if (response.isSuccessful) {
                Result.success("Asset created")
            } else {
                val errorBody = response.errorBody()?.string() ?: "No error body"
                val error = "Failed to create asset: ${response.code()} - ${response.message()} | $errorBody"
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun startTrackingWithAsset(deviceId: String, customerId: String): Result<String> {
        return try {
            val payload = TrackingStartPayload(
                deviceId = deviceId,
                assetIdentifier = AssetIdentifier(customerId = customerId)
            )

            val response = executeWithTokenRetry { authHeader ->
                apiService.startTracking(authHeader, payload)
            }

            if (response.isSuccessful) {
                Result.success("Tracking started with asset")
            } else {
                val errorBody = response.errorBody()?.string() ?: "No error body"
                val error = "Failed to start tracking with asset: ${response.code()} - ${response.message()} | $errorBody"
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun createTrip(customerId: String): Result<String> {
        return try {
            val payload = TripPayload(
                trip = Trip(customerId = customerId)
            )

            val response = executeWithTokenRetry { authHeader ->
                apiService.createTripNew(authHeader, payload)
            }

            if (response.isSuccessful) {
                Result.success("Trip created")
            } else {
                val errorBody = response.errorBody()?.string() ?: "No error body"
                val error = "Failed to create trip: ${response.code()} - ${response.message()} | $errorBody"
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun updateTripStage(customerId: String): Result<String> {
        return try {
            val payload = TripUpdateStagePayload(
                newStage = "IN_TRANSIT",
                tripIdentifier = TripIdentifier(customerId = customerId)
            )

            val response = executeWithTokenRetry { authHeader ->
                apiService.updateTripStage(authHeader, payload)
            }

            if (response.isSuccessful) {
                Result.success("Trip stage updated")
            } else {
                val errorBody = response.errorBody()?.string() ?: "No error body"
                val error = "Failed to update trip stage: ${response.code()} - ${response.message()} | $errorBody"
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun startTrackingWithTrip(deviceId: String, customerId: String): Result<String> {
        return try {
            val payload = TrackingStartPayload(
                deviceId = deviceId,
                tripIdentifier = TripIdentifier(customerId = customerId)
            )

            val response = executeWithTokenRetry { authHeader ->
                apiService.startTracking(authHeader, payload)
            }

            if (response.isSuccessful) {
                Result.success("Tracking started with trip")
            } else {
                val errorBody = response.errorBody()?.string() ?: "No error body"
                val error = "Failed to start tracking with trip: ${response.code()} - ${response.message()} | $errorBody"
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Legacy method - Submit multiple pairs sequentially
     */
    suspend fun submitMultiplePairs(deviceId: String, toteIds: List<String>, olpns: List<String>): Result<String> {
        val results = mutableListOf<String>()
        val errors = mutableListOf<String>()

        val maxSize = maxOf(toteIds.size, olpns.size)

        for (i in 0 until maxSize) {
            val toteId = if (i < toteIds.size) toteIds[i] else "N/A"
            val olpn = if (i < olpns.size) olpns[i] else "N/A"

            if (toteId != "N/A" && olpn != "N/A") {
                val result = submitSinglePair(deviceId, toteId, olpn)

                if (result.isSuccess) {
                    results.add(result.getOrNull() ?: "Success")
                } else {
                    val error = result.exceptionOrNull()?.message ?: "Unknown error"
                    errors.add("Failed for ToteId: $toteId, OLPN: $olpn - $error")
                }

                delay(1000)
            }
        }

        return if (errors.isEmpty()) {
            Result.success("Successfully submitted ${results.size} pairs")
        } else {
            val errorMessage = "Completed with errors. Successful: ${results.size}, Failed: ${errors.size}\nErrors: ${errors.joinToString("\n")}"
            Result.failure(Exception(errorMessage))
        }
    }

    // ===========================
    // LEGACY BARCODE METHODS (For ScanViewModel.kt compatibility)
    // ===========================

    /**
     * Legacy method for posting barcode data - used by ScanViewModel.kt
     * @Deprecated This method is kept for backward compatibility only
     */
    @Deprecated("This method is kept for backward compatibility only")
    suspend fun postBarcode(payload: BarcodePayload): Result<String> {
        return try {
            Log.d(TAG, "Legacy postBarcode called with: ${payload.value}, format: ${payload.format}")

            // For backward compatibility, we can either:
            // 1. Just return success without making an API call (current implementation)
            // 2. Or make an actual API call if the endpoint exists

            // Option 1: Simple success response (recommended for backward compatibility)
            Result.success("Barcode data received: ${payload.value}")

            // Option 2: Uncomment below if you want to make an actual API call
            /*
            val response = executeWithTokenRetry { authHeader ->
                apiService.postBarcode(authHeader, payload)
            }

            if (response.isSuccessful) {
                Result.success("Barcode posted successfully: ${payload.value}")
            } else {
                val errorBody = response.errorBody()?.string() ?: "No error body"
                val error = "Failed to post barcode: ${response.code()} - ${response.message()} | $errorBody"
                Result.failure(Exception(error))
            }
            */
        } catch (e: Exception) {
            Log.e(TAG, "Exception in legacy postBarcode", e)
            Result.failure(e)
        }
    }
}

// ===========================
// DATA CLASSES FOR NEW API PAYLOADS
// ===========================

// API #1: List trips payload
data class ListTripsInTransitPayload(
    val tripStages: List<String>,
    val assetIdentifier: AssetIdentifier
)

// API #3: End tracking payload
data class EndTrackingPayload(
    val assetIdentifier: AssetIdentifier,
    val tripIdentifier: TripIdentifier
)

// API #4: Create trip payload
data class CreateTripPayload(
    val trip: TripData
)

data class TripData(
    val customer_id: String // Using snake_case as per Python payload
)

// API #5: Add trip tracking payload
data class AddTripTrackingPayload(
    val assetIdentifier: AssetIdentifier,
    val tripIdentifier: TripIdentifier
)

// Existing data classes (keep for backward compatibility)
data class AssetIdentifier(val customerId: String)
data class TripIdentifier(val customerId: String)

data class TripUpdateStagePayload(
    val tripIdentifier: TripIdentifier,
    val newStage: String
)

// Response classes
data class ListTripsResponse(
    val trips: List<TripInfo>? = null
)

data class TripInfo(
    val customerId: String? = null,
    val status: String? = null,
    val stage: String? = null
)

// Legacy data classes (keep for backward compatibility)
data class ListTripsPayload(val deviceId: String)
data class AssetPayload(val asset: Asset)
data class Asset(val customerId: String)
data class TripPayload(val trip: Trip)
data class Trip(val customerId: String? = null, val customer_id: String? = null)
data class TrackingStartPayload(
    val deviceId: String? = null,
    val assetIdentifier: AssetIdentifier? = null,
    val tripIdentifier: TripIdentifier? = null
)

// Missing legacy classes that ScanViewModel.kt is trying to use
data class BarcodePayload(
    val value: String,
    val format: String, // Changed from Int to String to match ScanViewModel usage
    val timestamp: Long = System.currentTimeMillis()
)