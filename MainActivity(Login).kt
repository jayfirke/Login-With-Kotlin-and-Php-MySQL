package com.firke.epoi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

@SuppressLint("StaticFieldLeak")
lateinit var username_Et: EditText
@SuppressLint("StaticFieldLeak")
lateinit var password_Et: EditText
@SuppressLint("StaticFieldLeak")
lateinit var submit_Bu: Button

class MainActivity : AppCompatActivity() {

	var loginmehtod = "login"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		username_Et = findViewById(R.id.username_et)
		password_Et = findViewById(R.id.password_et)
		submit_Bu = findViewById(R.id.submit_bu)

		submit_Bu.setOnClickListener {
			submit()
		}
	}

	private fun submit() {
		var username = username_Et.text.toString()
		var password = password_Et.text.toString()
		println("username************************" + username)
		println("password************************" + password)

		var URL_ROOT = "http://192.168.1.8:8080/epoi/getdata.php?method=" + loginmehtod + "&username=" + username + "&password=" + password

		//creating volley string request
		val stringRequest = @SuppressLint("ApplySharedPref")
		object : StringRequest(Request.Method.GET, URL_ROOT,
				Response.Listener<String> { response ->
					try {
						val obj = JSONObject(response)

						var loginresponse = obj.getString("response")
						var loginmuserid = obj.getString("userid")
						var loginuseridstr = loginmuserid.toString()
						var StrResp = loginresponse.toString().trim()
						id = loginmuserid
						if(StrResp=="true") {
							val intent = Intent(this, StudentsActivity::class.java)
							startActivity(intent)
							Toast.makeText(this, "You are logged in", Toast.LENGTH_LONG).show()
						}else{
							Toast.makeText(this, "Account does not exist", Toast.LENGTH_SHORT).show()
						}
						println(StrResp + "**************************************")
						println(loginuseridstr + "**************************************")
					} catch (e: JSONException) {
						e.printStackTrace()
					}
				},
				object : Response.ErrorListener {
					override fun onErrorResponse(volleyError: VolleyError) {
						Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_SHORT).show()
					}
				}) {
			@Throws(AuthFailureError::class)
			override fun getParams(): Map<String, String> {
				val params = HashMap<String, String>()
				params.put("method", "login")
				params.put("username", username)
				params.put("password", password)
				return params
			}
		}
		//adding request to queue
		VolleySingleton.instance?.addToRequestQueue(stringRequest)
	}
}
