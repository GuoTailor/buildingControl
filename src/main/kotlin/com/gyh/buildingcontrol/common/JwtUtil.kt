package com.gyh.buildingcontrol.common

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import java.util.*


/**
 * Created by gyh on 2022/2/26
 */
object JwtUtil {
    const val secret =
        "VFVsSFprMUJNRWREVTNGSFUwbGlNMFJSUlVKQlVWVkJRVFJIVGtGRVEwSnBVVXRDWjFGRVpHeGhkRkpxVW1wdloyOHpWMjlxWjBkSVJraFpUSFZ" +
                "uWkFwVlYwRlpPV2xTTTJaNU5HRnlWMDVCTVV0dlV6aHJWbmN6TTJOS2FXSlljamhpZG5kVlFWVndZWEpEZDJ4 MlpHSklObVIyUlU5bWI" +
                "zVXdMMmREUmxGekNraFZabEZ5VTBSMkswMTFVMVZOUVdVNGFucExSVFJ4Vnl0cVN5dDRVVlU1WVRBe lIxVnVTMGhyYTJ4bEsxRXdjRmd" +
                "2WnpacVdGbzNjakV2ZUVGTE5VUUtiekpyVVN0WU5YaExPV05wY0ZKblJVdDNTVVJCVVVGQwog"

    //                    60秒     分    时   天
    const val ttlMillis = 60000L * 60 * 24 * 3

    fun generateToken(user: BaseUser): String? {
        try {
            val algorithm: Algorithm = Algorithm.HMAC256(secret)
            val date = Date();
            return JWT.create()
                .withClaim("id", user.id)
                .withClaim("username", user.username)
                .withClaim("roles", user.getRoles())
                .withIssuer("auth0")
                .withIssuedAt(date)
                .withExpiresAt(Date(date.time + ttlMillis))
                .sign(algorithm)
        } catch (exception: JWTCreationException) {
            exception.printStackTrace()
        }
        return null
    }

    fun <T : BaseUser> parseToken(token: String, user: T): T {
        val algorithm = Algorithm.HMAC256(secret)
        val verifier = JWT.require(algorithm)
            .withIssuer("auth0")
            .build() //Reusable verifier instance
        val jwt = verifier.verify(token)
        user.id = jwt.getClaim("id").asInt()
        user.username = jwt.getClaim("username").asString()
        user.setRoles(jwt.getClaim("roles").asList(String::class.java))
        return user
    }

}