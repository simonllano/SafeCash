package com.simonllano.safecash.data

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.simonllano.safecash.data.model.User
import kotlinx.coroutines.tasks.await

class UserRepository {

    private var auth: FirebaseAuth = Firebase.auth
    private var db = Firebase.firestore

    suspend fun registerUser(email: String, password: String) : ResourceRemote<String?> {
        return try{
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        ResourceRemote.Success(data = result.user?.uid)
        }
        catch (e: FirebaseAuthException) { // Esta sociado a problemas de autienticacion
            Log.e("RegisterMessageError", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }

        catch (e: FirebaseNetworkException){ // Puede tener problemas por problemas de red
            Log.e("RegisterNetwork", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }

    }

    suspend fun loginUser(email: String, password: String): ResourceRemote<String?> {
        return try{
            val result = auth.signInWithEmailAndPassword(email, password).await()
            ResourceRemote.Success(data = result.user?.uid)
        }
        catch (e: FirebaseAuthException) { // Esta sociado a problemas de autienticacion
            Log.e("LoginAuthError", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }

        catch (e: FirebaseNetworkException){ // Puede tener problemas por problemas de red
            Log.e("LoginNetwork", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }
        catch (e: FirebaseException){ // Puede tener problemas por problemas de red
            Log.e("LoginException", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

    suspend fun createUser(user: User): ResourceRemote<String?>{
        return try {
            user.uid?.let { db.collection("users").document(it).set(user).await() }
            ResourceRemote.Success(data = user.uid)
        } catch (e: FirebaseFirestoreException){
            Log.e( "FirebaseFirestoreException",e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e:FirebaseNetworkException){
            Log.e("FirebaseNetworkException",e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }catch (e: FirebaseException){
            Log.e("FirebaseException",e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

    suspend fun loadUser(uid: String): ResourceRemote<User?> {
        return try {
            val documentSnapshot = db.collection("users").document(uid).get().await()
            val user = documentSnapshot.toObject(User::class.java)
            user?.email = auth.currentUser?.email
            ResourceRemote.Success(data = user)
        } catch (e: FirebaseFirestoreException) {
            Log.e("FirebaseFirestoreError", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseNetworkException) {
            Log.e("FirebaseNetworkException", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseException) {
            Log.e("FirebaseException", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

    suspend fun verifyUser(): ResourceRemote<Boolean> {
        return try {
            val userLoaded = auth.currentUser != null
            ResourceRemote.Success(data = userLoaded)
        }
        catch (e: FirebaseAuthException) { // Esta sociado a problemas de autienticacion
            Log.e("LoginAuthError", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }

        catch (e: FirebaseNetworkException){ // Puede tener problemas por problemas de red
            Log.e("LoginNetwork", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }
        catch (e: FirebaseException){ // Puede tener problemas por problemas de red
            Log.e("LoginException", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

    fun signOut(): ResourceRemote<Boolean> {
        return try {
            auth.signOut()
            ResourceRemote.Success(data = true)
        }
        catch (e: FirebaseAuthException) { // Esta sociado a problemas de autienticacion
            Log.e("LoginAuthError", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }

        catch (e: FirebaseNetworkException){ // Puede tener problemas por problemas de red
            Log.e("LoginNetwork", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }
        catch (e: FirebaseException){ // Puede tener problemas por problemas de red
            Log.e("LoginException", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

}