package com.gosocial.zoom

/**
 * Created by Saunik Singh on 23/3/21.
 */
object Constants {
    // TODO Change it to your web domain
    val WEB_DOMAIN = "zoom.us"

    // TODO change it to your user ID
    var USER_ID = "_WqRflzRRLSaNjL0fwREkw"

    // TODO change it to your token
    var ZOOM_ACCESS_TOKEN = "eyJ6bV9za20iOiJ6bV9vMm0iLCJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJjbGllbnQiLCJ1aWQiOiJfV3FSZmx6UlJMU2FOakwwZndSRWt3IiwiaXNzIjoid2ViIiwic3R5IjoxLCJ3Y2QiOiJhdzEiLCJjbHQiOjAsInN0ayI6IklDR0lqZE1feXZJZS1XQm5tbnJOaUp4eUtNU3o1dVE3MlpxQk14RGhuNVkuQUcuMDVZdFZjTjdCelJRMjQ0LTVYYm42QTdYakNCNDktdjZyWUpueHJrazkwU0lZQl9BSjA1OHVBdEJLamFwN2xhLWgxR00xWVh3OV82TDVGZy5waFB0Y2N6bms3d0c3TTFEQXJ1VHJnLmVRdFFPQ3N6TjFiU3NQa3giLCJleHAiOjE2MTY0OTAxMjksImlhdCI6MTYxNjQ4MjkyOSwiYWlkIjoiN0JDN2lRTDFScTJtMG93NlpyRXVFQSIsImNpZCI6IiJ9.xr2mIYFndikZIcwGgE76Aqt7itX8LmS3obDVqev6__I"

    // TODO Change it to your exist meeting ID to start meeting
    var MEETING_ID: String? = "94667351895"

    /**
     * We recommend that, you can generate jwttoken on your own server instead of hardcore in the code.
     * We hardcore it here, just to run the demo.
     *
     * You can generate a jwttoken on the https://jwt.io/
     * with this payload:
     * {
     * "appKey": "string", // app key
     * "iat": long, // access token issue timestamp
     * "exp": long, // access token expire time
     * "tokenExp": long // token expire time
     * }
     */
    val SDK_JWTTOKEN: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOm51bGwsImlzcyI6ImN0WXhiVE95U0VheEpNX2t6dVNXR2ciLCJleHAiOjI1MjQ2NzQ1NDAsImlhdCI6MTYxNjQ3NzU3M30.YwHZ3mpgqKQkjezJXeyxHg8faXKwLB1cdAF3L7Yqk64"
}