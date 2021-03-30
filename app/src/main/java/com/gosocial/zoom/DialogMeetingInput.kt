package com.gosocial.zoom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

/**
 * Created by Saunik Singh on 25/3/21.
 */
class DialogMeetingInput(context: Context) : Dialog(context) {
    var dialogMeetingInputSubmission: OnMeetingInputSubmission? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_meeting)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val meetingIdTextView = findViewById<EditText>(R.id.meetingId)
        val passCodeEditText = findViewById<EditText>(R.id.passcode)
        btnSubmit.setOnClickListener {
            if (!TextUtils.isEmpty(meetingIdTextView.text) && !TextUtils.isEmpty(passCodeEditText.text)) {
                dismiss()
                dialogMeetingInputSubmission?.onStart(
                    meetingIdTextView.text.toString(),
                    passCodeEditText.text.toString()
                )
            } else {
                Toast.makeText(
                    context,
                    "Please Enter Correct Id & passcode to join a meeting",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}