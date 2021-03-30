package com.gosocial.zoom

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import us.zoom.sdk.MeetingActivity

/**
 * Created by Saunik Singh on 23/3/21.
 */
class MyMeetingActivity : MeetingActivity() {
    private var btnLeaveZoomMeeting: Button? = null
    private var btnSwitchToNextCamera: Button? = null
    private var btnAudio: Button? = null
    private var btnParticipants: Button? = null
    private var btnShare: Button? = null
    private var btnStopShare: Button? = null
    private var btnMoreOptions: Button? = null

    private var viewTabMeeting: View? = null
    private var btnTabWelcome: Button? = null
    private var btnTabMeeting: Button? = null
    private var btnTabPage2: Button? = null

    override fun getLayout(): Int {
        return R.layout.my_meeting_layout
    }

    override fun isAlwaysFullScreen(): Boolean {
        return false
    }

    override fun isSensorOrientationEnabled(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disableFullScreenMode()
        setupTabs()
        btnLeaveZoomMeeting = findViewById<View>(R.id.btnLeaveZoomMeeting) as Button
        btnSwitchToNextCamera = findViewById<View>(R.id.btnSwitchToNextCamera) as Button
        btnAudio = findViewById<View>(R.id.btnAudio) as Button
        btnParticipants = findViewById<View>(R.id.btnParticipants) as Button
        btnShare = findViewById<View>(R.id.btnShare) as Button
        btnStopShare = findViewById<View>(R.id.btnStopShare) as Button
        btnMoreOptions = findViewById<View>(R.id.btnMoreOptions) as Button
        btnLeaveZoomMeeting?.setOnClickListener { showLeaveDialog() }
        btnSwitchToNextCamera?.setOnClickListener { switchToNextCamera() }
        btnAudio?.setOnClickListener { //doAudioAction();
            if (!isAudioConnected) {
                connectVoIP()
            } else {
                muteAudio(!isAudioMuted)
            }
        }
        btnParticipants?.setOnClickListener { showParticipants() }
        btnShare?.setOnClickListener { showShareOptions() }
        btnStopShare?.setOnClickListener { stopShare() }
        btnMoreOptions?.setOnClickListener{ showMoreOptions() }
    }

    private fun selectTab(tabId: Int) {
        if (tabId == MainActivity.TAB_MEETING) {
            btnTabWelcome!!.isSelected = false
            btnTabPage2!!.isSelected = false
            btnTabMeeting!!.isSelected = true
        } else {
            switchToMainActivity(tabId)
        }
    }

    private fun switchToMainActivity(tab: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.action = MainActivity.ACTION_RETURN_FROM_MEETING
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        intent.putExtra(MainActivity.EXTRA_TAB_ID, tab)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        updateButtonsStatus()

        // disable animation
        overridePendingTransition(0, 0)
    }

    override fun onMeetingConnected() {
        updateButtonsStatus()
    }

    override fun onSilentModeChanged(inSilentMode: Boolean) {
        updateButtonsStatus()
    }

    override fun onStartShare() {
        btnShare!!.visibility = View.GONE
        btnStopShare!!.visibility = View.VISIBLE
    }

    override fun onStopShare() {
        btnShare!!.visibility = View.VISIBLE
        btnStopShare!!.visibility = View.GONE
    }

    private fun updateButtonsStatus() {
        val enabled = isMeetingConnected && !isInSilentMode
        btnSwitchToNextCamera!!.isEnabled = enabled
        btnAudio!!.isEnabled = enabled
        btnParticipants!!.isEnabled = enabled
        btnShare!!.isEnabled = enabled
        btnMoreOptions!!.isEnabled = enabled
        if (isSharingOut) {
            btnShare!!.visibility = View.GONE
            btnStopShare!!.visibility = View.VISIBLE
        } else {
            btnShare!!.visibility = View.VISIBLE
            btnStopShare!!.visibility = View.GONE
        }
    }

    private fun disableFullScreenMode() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN.inv(), WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun setupTabs() {
        viewTabMeeting = findViewById(R.id.viewTabMeeting)
        btnTabWelcome = findViewById<View>(R.id.btnTabWelcome) as Button
        btnTabMeeting = findViewById<View>(R.id.btnTabMeeting) as Button
        btnTabPage2 = findViewById<View>(R.id.btnTabPage2) as Button
        selectTab(MainActivity.TAB_MEETING)
        btnTabMeeting?.setOnClickListener { selectTab(MainActivity.TAB_MEETING) }
        btnTabWelcome?.setOnClickListener { selectTab(MainActivity.TAB_WELCOME) }
        btnTabPage2?.setOnClickListener { selectTab(MainActivity.TAB_PAGE_2) }
    }

    override fun onConfigurationChanged(p0: Configuration) {
        super.onConfigurationChanged(p0)
        disableFullScreenMode()
    }
}