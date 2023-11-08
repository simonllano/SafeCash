package com.simonllano.safecash.data

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.simonllano.safecash.data.model.Deductibles
import kotlinx.coroutines.tasks.await

class DeductiblesRepository {

    private var db = Firebase.firestore
    suspend fun createDeductible(deductibles: Deductibles): ResourceRemote<String?> {
        return try {
            val document = db.collection("deductibles").document()
            deductibles.id = document.id
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
}