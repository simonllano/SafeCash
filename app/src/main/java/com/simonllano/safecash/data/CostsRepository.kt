package com.simonllano.safecash.data

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.simonllano.safecash.data.model.Costs
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class CostsRepository {

    private var db = Firebase.firestore

    suspend fun createCosts(costs: Costs): ResourceRemote<String?> {
        return try {
            val document = db.collection("costs").document()
            costs.id = document.id
            db.collection("costs").document(document.id).set(costs).await()
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
        } catch (e: NumberFormatException) {
            Log.e("Cadena vacia", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
            // Manejo de caso cuando la cadena no es un número válido
        }
    }

    suspend fun loadDatos(): ResourceRemote<QuerySnapshot?> {
        return try {
            val result = db.collection("costs").get().await()
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


