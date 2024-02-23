package com.testeapp.emprestimo

class User {
    var fullName: String? = null
    var email: String? = null
    var phone: String? = null
    var userID: String? = null
    var password: String? = null
    var fcmToken: String? = null
    var installationId: String? = null
    var registrationDateTime: String? = null
    var cpf: String? = null

    constructor()

    constructor(
        fullName: String?,
        email: String?,
        phone: String?,
        userID: String?,
        token: String?,
        cpf: String?
    ) {
        this.fullName = fullName
        this.email = email
        this.phone = phone
        this.userID = userID
        this.fcmToken = token
        this.cpf = cpf
    }

    constructor(fullName: String?, email: String?, phone: String?, password: String?) {
        this.fullName = fullName
        this.email = email
        this.phone = phone
        this.password = password
    }

    fun toMap(): Map<String, Any?> {
        val userMap = HashMap<String, Any?>()
        userMap["fullName"] = fullName
        userMap["email"] = email
        userMap["phone"] = phone
        userMap["userID"] = userID
        userMap["password"] = password
        userMap["fcmToken"] = fcmToken
        userMap["installationId"] = installationId
        userMap["registrationDateTime"] = registrationDateTime
        userMap["cpf"] = cpf
        return userMap
    }
}

