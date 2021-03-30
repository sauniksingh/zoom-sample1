package com.gosocial.zoom

/**
 * Created by Saunik Singh on 25/3/21.
 */
interface OnMeetingInputSubmission {

    fun onStart(meetingId: String, passcode: String)
}