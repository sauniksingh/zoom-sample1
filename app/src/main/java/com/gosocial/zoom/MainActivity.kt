package com.gosocial.zoom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.gosocial.zoom.Constants.MEETING_ID
import com.gosocial.zoom.Constants.WEB_DOMAIN
import us.zoom.sdk.*


class MainActivity : Activity(), MeetingServiceListener, ZoomSDKInitializeListener,
    OnMeetingInputSubmission {
    private val TAG = "Zoom SDK Example"

    companion object {
        val TAB_WELCOME = 1
        val TAB_MEETING = 2
        val TAB_PAGE_2 = 3
        val ACTION_RETURN_FROM_MEETING = "us.zoom.sdkexample2.action.ReturnFromMeeting"
        val EXTRA_TAB_ID = "tabId"
    }

    private val STYPE = MeetingService.USER_TYPE_API_USER
    private val DISPLAY_NAME = "ZoomUS SDK"

    private var viewTabWelcome: View? = null
    private var viewTabMeeting: View? = null
    private var viewTabPage2: View? = null
    private var btnTabWelcome: Button? = null
    private var btnTabMeeting: Button? = null
    private var btnTabPage2: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupTabs()

        val zoomSDK = ZoomSDK.getInstance()

        if (savedInstanceState == null) {
            val initParams = ZoomSDKInitParams()
//            initParams.jwtToken = SDK_JWTTOKEN
            initParams.domain = WEB_DOMAIN
            initParams.appKey = "eh653FOACqT1qLKa5qYgf8PeHGYvcbQtqxa0"
            initParams.appSecret = "anGDxsEAnTD0Gq9eT7bTgQZ7jzGznT22Cwoi"
//            initParams.enableLog = true
//            initParams.logSize = 50
            zoomSDK.initialize(this, this, initParams)
        }

        if (zoomSDK.isInitialized) {
            registerMeetingServiceListener()
        }
    }

    override fun onMeetingStatusChanged(p0: MeetingStatus?, p1: Int, p2: Int) {
        if (p0 == MeetingStatus.MEETING_STATUS_FAILED && p1 == MeetingError.MEETING_ERROR_CLIENT_INCOMPATIBLE) {
            Toast.makeText(this, "Version of ZoomSDK is too low!", Toast.LENGTH_LONG).show()
        }

        if (p0 == MeetingStatus.MEETING_STATUS_IDLE || p0 == MeetingStatus.MEETING_STATUS_FAILED) {
            selectTab(TAB_WELCOME)
        }
    }

    override fun onZoomSDKInitializeResult(p0: Int, p1: Int) {
        Log.i(TAG, "onZoomSDKInitializeResult, errorCode=$p0, internalErrorCode=$p1")

        if (p0 != ZoomError.ZOOM_ERROR_SUCCESS) {
            Log.e("Zoom-error", "Failed to initialize Zoom SDK. Error: $p0, internalErrorCode=$p1")
            Toast.makeText(
                this,
                "Failed to initialize Zoom SDK. Error: $p0, internalErrorCode=$p1",
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(this, "Initialize Zoom SDK successfully.", Toast.LENGTH_LONG).show()
            registerMeetingServiceListener()
        }
    }

    override fun onZoomAuthIdentityExpired() {
    }

    private fun setupTabs() {
        viewTabWelcome = findViewById(R.id.viewTabWelcome)
        viewTabMeeting = findViewById(R.id.viewTabMeeting)
        viewTabPage2 = findViewById(R.id.viewTabPage2)
        btnTabWelcome = findViewById(R.id.btnTabWelcome)
        btnTabMeeting = findViewById(R.id.btnTabMeeting)
        btnTabPage2 = findViewById(R.id.btnTabPage2)
        selectTab(TAB_WELCOME)
        btnTabMeeting?.setOnClickListener { selectTab(TAB_MEETING) }
        btnTabWelcome?.setOnClickListener { selectTab(TAB_WELCOME) }
        btnTabPage2?.setOnClickListener { selectTab(TAB_PAGE_2) }
    }

    private fun selectTab(tabId: Int) {
        if (tabId == TAB_WELCOME) {
            viewTabWelcome!!.visibility = View.VISIBLE
            viewTabMeeting!!.visibility = View.GONE
            viewTabPage2!!.visibility = View.GONE
            btnTabWelcome!!.isSelected = true
            btnTabMeeting!!.isSelected = false
            btnTabPage2!!.isSelected = false
        } else if (tabId == TAB_PAGE_2) {
            viewTabWelcome!!.visibility = View.GONE
            viewTabMeeting!!.visibility = View.GONE
            viewTabPage2!!.visibility = View.VISIBLE
            btnTabWelcome!!.isSelected = false
            btnTabMeeting!!.isSelected = false
            btnTabPage2!!.isSelected = true
        } else if (tabId == TAB_MEETING) {
            val dialogMeetingInput = DialogMeetingInput(this)
            dialogMeetingInput.dialogMeetingInputSubmission = this
            dialogMeetingInput.show()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        // disable animation
        overridePendingTransition(0, 0)
        val action = intent.action
        if (ACTION_RETURN_FROM_MEETING == action) {
            val tabId = intent.getIntExtra(EXTRA_TAB_ID, TAB_WELCOME)
            selectTab(tabId)
        }
    }

    private fun registerMeetingServiceListener() {
        val zoomSDK = ZoomSDK.getInstance()
        val meetingService = zoomSDK.meetingService
        meetingService?.addListener(this)
    }

    override fun onDestroy() {
        val zoomSDK = ZoomSDK.getInstance()
        if (zoomSDK.isInitialized) {
            val meetingService = zoomSDK.meetingService
            meetingService.removeListener(this)
        }
        super.onDestroy()
    }

    fun startMeeting(meetingId: String, passcode: String) {
        val zoomSDK = ZoomSDK.getInstance()
        if (!zoomSDK.isInitialized) {
            Toast.makeText(this, "ZoomSDK has not been initialized successfully", Toast.LENGTH_LONG)
                .show()
            return
        }
        if (MEETING_ID == null) {
            Toast.makeText(this, "MEETING_ID in Constants can not be NULL", Toast.LENGTH_LONG)
                .show()
            return
        }
        val meetingService = zoomSDK.meetingService

        /* // It's for start Meeting by self
        val opts = StartMeetingOptions()
        opts.no_driving_mode = true
        //		opts.no_meeting_end_message = true;
        opts.no_titlebar = true
        opts.no_bottom_toolbar = true
        opts.no_invite = true
        val params = StartMeetingParamsWithoutLogin()
        params.userId = USER_ID
        params.zoomAccessToken = ZOOM_ACCESS_TOKEN
        params.meetingNo = MEETING_ID
        params.displayName = DISPLAY_NAME
        val ret = meetingService.startMeetingWithParams(this, params, opts)
*/
//        Log.i(TAG, "onClickBtnStartMeeting, ret=$ret")

        // For Joint a existing meeting
        val jots = JoinMeetingOptions()
        jots.no_audio = true
        val joinParam = JoinMeetingParams()
        joinParam.displayName = "Hapramp"
        joinParam.meetingNo = /*"94667351895"*/ meetingId
        joinParam.password = /*"474701"*/ passcode
        val ret = meetingService.joinMeetingWithParams(this, joinParam, jots)
        Log.i(TAG, "onClickBtnStartMeeting, ret=$ret")
    }

    override fun onStart(meetingId: String, passcode: String) {
        val zoomSDK = ZoomSDK.getInstance()
        if (!zoomSDK.isInitialized) {
            Toast.makeText(this, "ZoomSDK has not been initialized successfully", Toast.LENGTH_LONG)
                .show()
            return
        }
        val meetingService = zoomSDK.meetingService ?: return
        if (meetingService.meetingStatus == MeetingStatus.MEETING_STATUS_IDLE) {
            viewTabWelcome!!.visibility = View.GONE
            viewTabPage2!!.visibility = View.GONE
            viewTabMeeting!!.visibility = View.VISIBLE
            btnTabWelcome!!.isSelected = false
            btnTabPage2!!.isSelected = false
            btnTabMeeting!!.isSelected = true
            startMeeting(meetingId, passcode)
        } else {
            meetingService.returnToMeeting(this)
        }
        overridePendingTransition(0, 0)
    }
}