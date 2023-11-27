package com.simonllano.safecash.data

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.simonllano.safecash.data.model.Deductibles
import kotlinx.coroutines.tasks.await

class DeductiblesRepository {

    private var db = Firebase.firestore
    private var auth: FirebaseAuth = Firebase.auth
    suspend fun createDeductible(deductibles: Deductibles): ResourceRemote<String?> {
        return try {
            val document = db.collection("deductibles").document()
            deductibles.id = document.id
            deductibles.uid = auth.uid
            db.collection("deductibles").document(document.id).set(deductibles).await()

            ResourceRemote.Success(data = document.id)
        } catch (e: FirebaseFirestoreException) {
            Log.e("FirebaseFirestoreException", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseNetworkException) {
            Log.e("FirebaseNetworkException", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseException) {
            Log.e("FirebaseException", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

    suspend fun loadDatos(): ResourceRemote<QuerySnapshot?> {
        return try {
            val result = db.collection("deductibles").get().await()
            ResourceRemote.Success(data = result)
        } catch (e: FirebaseFirestoreException) {
            Log.e("FirebaseFirestoreException", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseNetworkException) {
            Log.e("FirebaseNetworkException", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseException) {
            Log.e("FirebaseException", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }
}