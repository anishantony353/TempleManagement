package com.saarit.temple_management.templemanagement.view.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.saarit.temple_management.templemanagement.R
import com.saarit.temple_management.templemanagement.databinding.ActivityMapBinding
import com.saarit.temple_management.templemanagement.model.FormType_1
import com.saarit.temple_management.templemanagement.util.Constant
import com.saarit.temple_management.templemanagement.util.PrefManager.getUserTypeId
import com.saarit.temple_management.templemanagement.util.Utility
import com.saarit.temple_management.templemanagement.view_model.MapViewModel

class ActivityMap : AppCompatActivity(), OnMapReadyCallback, OnMarkerDragListener, OnInfoWindowClickListener {
    private val TAG = ActivityMap::class.java.simpleName

    lateinit var binding: ActivityMapBinding
    lateinit var viewModel: MapViewModel
    var map: GoogleMap? = null
    var selectedMarker: Marker? = null
    var dragMarkerInitialLoc: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Utility.log(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        setupBindings(savedInstanceState)
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        Utility.log(TAG, "setupBindings()")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        if (savedInstanceState == null) {
            Utility.log(TAG, "savedInstanceState == null")
            viewModel.init()
        }
        binding.viewmodel = viewModel
        setSupportActionBar(binding.toolbarActivityMap)
        setUpObservers()
        setUpmap()
    }

    private fun setUpObservers() {
        Utility.log(TAG, "Observed: setUpObservers()")
        viewModel.isAddButtonClicked.observe(
                this,
                Observer { value: Boolean? ->
                    val intent = Intent(baseContext, Form1Activity::class.java)
                    intent.putExtra("req_code", Constant.REQUEST_CODE_NEW_TEMPLE)
                    startActivityForResult(intent, Constant.REQUEST_CODE_NEW_TEMPLE)
                }
        )
        viewModel.templeToDisplay.observe(
                this,
                Observer { temple: FormType_1 -> addTempleOnMap(temple) }
        )
        viewModel.templeToDisplayFromServer.observe(
                this,
                Observer { temple: FormType_1 ->
                    Utility.log(TAG, "Observed: getTempleToDisplayFromServer()")
                    addServerTempleOnMap(temple)
                }
        )
        viewModel.shouldAnimateMap.observe(
                this,
                Observer { latlng: LatLng -> animateCamera(latlng.latitude, latlng.longitude) }
        )
        viewModel.isDragSuccess.observe(
                this,
                Observer { value: Boolean? ->
                    if (true) {
                        Utility.showToast("Updated Location", Toast.LENGTH_SHORT, application)
                    } else {
                        Utility.showToast("Failed to Update Location", Toast.LENGTH_SHORT, application)
                        selectedMarker!!.position = dragMarkerInitialLoc!!
                    }
                }
        )
        viewModel.observeLockScreenRequest().observe(
                this,
                Observer { shouldLock: Boolean ->
                    if (shouldLock) {
                        Utility.log(TAG, "Locking Screen...")
                        lockScreen()
                    } else {
                        Utility.log(TAG, "UnLocking Screen...")
                        unlockScreen()
                    }
                }
        )
    }

    private fun lockScreen() {
        window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun unlockScreen() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Utility.log(TAG, "onMapReady()")
        map = googleMap
        map!!.mapType = GoogleMap.MAP_TYPE_HYBRID
        map!!.uiSettings.isMapToolbarEnabled = false
        map!!.setOnMarkerDragListener(this)
        map!!.setOnInfoWindowClickListener(this)
    }

    private fun setUpmap() {
        if (map == null) {
            (supportFragmentManager.findFragmentById(R.id.map_ActivityMap) as SupportMapFragment?)!!.getMapAsync(this)
        }
    }

    private fun addTempleOnMap(temple: FormType_1) {
        val latLng = LatLng(temple.latitude, temple.longitude)
        val markerOpts = MarkerOptions().position(
                latLng)
        markerOpts.icon(BitmapDescriptorFactory.fromResource(R.drawable.temple_map_icon))
        val marker = map!!.addMarker(markerOpts)
        marker.isDraggable = true
        marker.title = temple.temple
        Utility.log(TAG, "Temple Name on marker:" + temple.temple)
        viewModel.hashmapLocalTemples[marker] = temple
    }

    private fun addServerTempleOnMap(temple: FormType_1) {
        val latLng = LatLng(temple.latitude, temple.longitude)
        val markerOpts = MarkerOptions().position(
                latLng)
        markerOpts.icon(BitmapDescriptorFactory.fromResource(R.drawable.temple_map_icon_2))
        val marker = map!!.addMarker(markerOpts)
        marker.isDraggable = false
        marker.title = temple.temple
        Utility.log(TAG, "Server Temple Name on marker:" + temple.temple)
        viewModel.hashmapServerTemples[marker] = temple
    }

    private fun animateCamera(lat: Double, lon: Double) {
        val cameraPosition = CameraPosition.Builder()
                .target(LatLng(lat, lon)).zoom(17f).build()
        map!!.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        if (getUserTypeId(applicationContext) != 1) {
            val item = menu.findItem(R.id.action_createUser)
            item.isVisible = false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.satellite -> map!!.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.hybrid -> map!!.mapType = GoogleMap.MAP_TYPE_HYBRID
            R.id.terrain -> map!!.mapType = GoogleMap.MAP_TYPE_TERRAIN
            R.id.normal -> map!!.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.action_getNearbyTemples -> {
                Utility.log(TAG, "Clicked Get Nearby Temples")
                if (Utility.hasPermission(applicationContext)) {
                    Utility.showToast("Getting Location", Toast.LENGTH_SHORT, applicationContext)
                    viewModel.getNearByTemples()
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            Constant.REQUEST_CODE_REQUEST_LOCATION_PERMISSION)
                }
            }
            R.id.action_clearNearbyTemples -> viewModel.clearServerTemplesFromMap()
            R.id.action_getLocalTemples -> viewModel.getLocalTemples()
            R.id.action_clearLocalTemples -> viewModel.clearLocalTemplesFromMap()
            R.id.action_createUser -> startActivity(Intent(baseContext, Activity_CreateUser::class.java))
            R.id.action_listLocalForms -> startActivity(Intent(baseContext,ActivityListForms::class.java))
        }
        invalidateOptionsMenu()
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            Constant.REQUEST_CODE_REQUEST_LOCATION_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // permission was granted
                    Utility.showToast("Getting Location", Toast.LENGTH_SHORT, applicationContext)
                    viewModel!!.getNearByTemples()
                } else { // permission denied
                    Utility.showToast("Permission Denied", Toast.LENGTH_SHORT, applicationContext)
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constant.REQUEST_CODE_NEW_TEMPLE, Constant.REQUEST_CODE_CLICK_SERVER_TREES -> when (resultCode) {
                Activity.RESULT_OK -> {
                    val formType1 = data!!.getSerializableExtra(Constant.KEY_ADDED_TEMPLE) as FormType_1
                    Utility.log(TAG, "onActivityResult()..id:" + formType1.id)
                    addTempleOnMap(formType1)
                    animateCamera(formType1.latitude, formType1.longitude)
                }
                Constant.RESULT_CODE_AFTER_FORM_SUMBIT -> {
                    val value = data!!.getIntExtra(Constant.KEY_RESHOW_LOCAL_TEMPLES, 0)
                    when (value) {
                        1 -> viewModel!!.getLocalTemples()
                    }
                }
                else -> {
                }
            }
            Constant.REQUEST_CODE_CLICK_LOCAL_TREES -> when (resultCode) {
                Activity.RESULT_OK -> {
                    val formType1 = data!!.getSerializableExtra(Constant.KEY_ADDED_TEMPLE) as FormType_1
                    Utility.log(TAG, "onActivityResult()..id:" + formType1.templeId)
                    selectedMarker!!.hideInfoWindow()
                    selectedMarker!!.title = formType1.temple
                }
                Constant.RESULT_CODE_AFTER_FORM_SUMBIT -> {
                    val value = data!!.getIntExtra(Constant.KEY_RESHOW_LOCAL_TEMPLES, 0)
                    when (value) {
                        1 -> viewModel!!.getLocalTemples()
                    }
                }
                else -> {
                }
            }
        }
    }

    override fun onMarkerDragStart(marker: Marker) {
        selectedMarker = marker
        val temple = viewModel!!.hashmapLocalTemples[selectedMarker]
        val latLng = LatLng(temple!!.latitude, temple.longitude)
        dragMarkerInitialLoc = latLng
    }

    override fun onMarkerDrag(marker: Marker) {}
    override fun onMarkerDragEnd(marker: Marker) {
        showAlertDialog(marker)
    }

    private fun showAlertDialog(m: Marker): Boolean {
        val alertDialogBuilder = AlertDialog.Builder(
                this)
        alertDialogBuilder.setTitle("Do you want to update the position?")
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(
                        "Yes"
                ) { dialog: DialogInterface?, id: Int -> viewModel!!.updateDragLocation(selectedMarker) }
                .setNegativeButton("No"
                ) { dialog: DialogInterface, id: Int ->
                    selectedMarker!!.position = dragMarkerInitialLoc!! // keep the marker where it was
                    dialog.cancel()
                }
        // create alert dialog
        val alertDialog = alertDialogBuilder.create()
        // show it
        alertDialog.show()
        return false
    }

    override fun onInfoWindowClick(marker: Marker) {
        Utility.log(TAG, "onInfoWindowClick()")
        selectedMarker = marker
        if (viewModel!!.hashmapServerTemples[marker] != null) {
            val i = Intent(this, Form1Activity::class.java)
            i.putExtra("temple_id", viewModel!!.hashmapServerTemples[marker]!!.templeId)
            i.putExtra("req_code", Constant.REQUEST_CODE_CLICK_SERVER_TREES)
            startActivityForResult(i, Constant.REQUEST_CODE_CLICK_SERVER_TREES)
        } else if (viewModel!!.hashmapLocalTemples[marker] != null) {
            val i = Intent(this, Form1Activity::class.java)
            i.putExtra("id", viewModel!!.hashmapLocalTemples[marker]!!.id)
            i.putExtra("temple_id", viewModel!!.hashmapLocalTemples[marker]!!.templeId)
            i.putExtra("req_code", Constant.REQUEST_CODE_CLICK_LOCAL_TREES)
            startActivityForResult(i, Constant.REQUEST_CODE_CLICK_LOCAL_TREES)
        }
    }

    override fun onPause() {
        Utility.log(TAG, "onPause()")
        super.onPause()
        /*viewModel.unRegisterReceiver();*/
    }

    override fun onStop() {
        Utility.log(TAG, "onStop()")
        super.onStop()
    }

    override fun onResume() {
        Utility.log(TAG, "onResume()")
        super.onResume()
        /*viewModel.setUpLocationBroadcastReceiver();*/
    }
}