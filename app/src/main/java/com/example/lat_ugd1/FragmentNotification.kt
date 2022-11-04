package com.example.lat_ugd1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentNotification: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }
//    private fun setupRecyclerView() {
//        noteAdapter = NoteAdapter2(arrayListOf(), object :
//            NoteAdapter2.OnAdapterListener{
//            override fun onClick(note: Note) {
////                Toast.makeText(applicationContext, note.title, Toast.LENGTH_SHORT).show()
//                intentEdit(note.id,Constant.TYPE_READ)
//            }
//            override fun onUpdate(note: Note) {
//                intentEdit(note.id, Constant.TYPE_UPDATE)
//            }
//            override fun onDelete(note: Note) {
//                deleteDialog(note)
//            }
//        })
////        list_notif.apply {
////            layoutManager = LinearLayoutManager(
////
////
////
////                applicationContext)
////            adapter = noteAdapter
////        }
//    }
//
//    private fun deleteDialog(note: Note){
////        val alertDialog = AlertDialog.Builder(this)
////        alertDialog.apply {
////            setTitle("Confirmation")
////            setMessage("Are You Sure to delete this data From ${note.title}?")
////            setNegativeButton("Cancel", DialogInterface.OnClickListener
////            { dialogInterface, i ->
////                dialogInterface.dismiss()
////            })
////            setPositiveButton("Delete", DialogInterface.OnClickListener
////            { dialogInterface, i ->
////                dialogInterface.dismiss()
////                CoroutineScope(Dispatchers.IO).launch {
////                    db?.noteDao()?.deleteNote(note)
////                    loadData()
////                }
////            })
////        }
////        alertDialog.show()
//    }
//    override fun onStart() {
//        super.onStart()
//        loadData()
//    }
//    //untuk load data yang tersimpan pada database yang sudah create data
//    fun loadData() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val notes = db?.noteDao()?.getNotes()
//            Log.d("MainActivity","dbResponse: $notes")
//            withContext(Dispatchers.Main){
//                if (notes != null) {
//                    noteAdapter.setData( notes )
//                }
//            }
//        }
//    }
//    fun setupListener() {
//        button_add.setOnClickListener{
//            intentEdit(0,Constant.TYPE_CREATE)
//        }
//    }
//    //pick data dari Id yang sebagai primary key
//    fun intentEdit(noteId : Int, intentType: Int){
////        startActivity(
////            Intent(applicationContext, EditActivity2::class.java)
////                .putExtra("intent_id", noteId)
////                .putExtra("intent_type", intentType)
////        )
//    }

}