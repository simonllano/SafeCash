import android.util.Patterns

fun emailValidator(text: String): Boolean{ //Envia un true si cumple con la estructura de un correo y false sino cumple
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(text).matches()
}