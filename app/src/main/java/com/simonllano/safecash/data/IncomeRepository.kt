package com.simonllano.safecash.data

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.simonllano.safecash.data.model.Income
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.ktx.auth

class IncomeRepository {

    private var db = Firebase.firestore
    private var auth: FirebaseAuth = Firebase.auth
    suspend fun createIncome(income: Income): ResourceRemote<String?>{
        return try {
            val document = db.collection("income").document()
            income.id = document.id
            income.uid = auth.uid
            db.collection("income").document(document.id).set(income).await()

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
            val result = db.collection("income").get().await()
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

