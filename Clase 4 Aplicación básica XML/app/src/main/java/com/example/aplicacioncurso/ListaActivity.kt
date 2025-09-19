package com.example.aplicacioncurso

import Contact
import ContactAdapter
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacioncurso.R
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

class ListaActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val contactos = cargarContactosDesdeJSON()
        recyclerView.adapter = ContactAdapter(contactos)
    }

    private fun cargarContactosDesdeJSON(): List<Contact> {
        val contactos = mutableListOf<Contact>()
        try {
            val inputStream = assets.open("contacts.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonStr = reader.readText()
            reader.close()

            val jsonArray = JSONArray(jsonStr)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                contactos.add(Contact(obj.getString("nombre"), obj.getString("telefono")))
            }
        } catch (e: Exception) {
            Log.e("JSON", "Error al leer JSON", e)
        }
        return contactos
    }
}