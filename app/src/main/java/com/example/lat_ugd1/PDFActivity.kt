package com.example.lat_ugd1

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.lat_ugd1.databinding.ActivityPdfactivityBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itextpdf.barcodes.BarcodeQRCode
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_interview.view.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class PDFActivity : AppCompatActivity() {
    private var binding: ActivityPdfactivityBinding?   = null
    lateinit var bundle: Bundle
    val dataUser = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfactivityBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)

        val back = view.back

        binding!!.buttonSave.setOnClickListener {
            val id = binding!!.editTextId.text.toString()
            val nama = binding!!.editTextName.text.toString()
            val perusahaan = binding!!.editTextPerusahaan.text.toString()
            val role = binding!!.editTextRole.text.toString()

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (id.isEmpty() && nama.isEmpty() && perusahaan.isEmpty() && role.isEmpty()) {
                        FancyToast.makeText(
                            applicationContext,
                            "Data Interview Tidak Boleh kosong",
                            FancyToast.LENGTH_SHORT,
                            FancyToast.ERROR,false
                        ).show()
                    } else {
                        createPdf(id, nama, perusahaan, role)
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

        }
        val idUser = getBundle()
        back.setOnClickListener{
            dataUser.putInt("idUser", idUser)
            val close = Intent(this@PDFActivity, MenuActivity::class.java)
            close.putExtra("idUser",dataUser)
            startActivity(close)
        }
    }

    fun getBundle():Int{
        bundle = intent.getBundleExtra("idUser")!!
        var idUser : Int = bundle.getInt("idUser")!!
        return idUser
    }

    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Throws(FileNotFoundException::class)
    private fun createPdf(id: String, nama: String, perusahaan: String, role: String) {
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val file = File(pdfPath, "Data Interview.pdf")
        FileOutputStream(file)

        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)
        pdfDocument.defaultPageSize = com.itextpdf.kernel.geom.PageSize.A4
        document.setMargins(5f, 5f, 5f, 5f)
        @SuppressLint("UseCompatLoadingForDrawables") val d = getDrawable(R.drawable.img_1)

        val bitmap = (d as BitmapDrawable)!!.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bitmapdata = stream.toByteArray()
        val imageData = ImageDataFactory.create(bitmapdata)
        val image = Image(imageData)
        val namapengguna = Paragraph("Data Interview").setBold().setFontSize(24f).setTextAlignment(TextAlignment.CENTER)
        val group = Paragraph (
            """
                Berikut adalah 
                Data Calon Interview Perusahaan 2022
            """.trimIndent()).setTextAlignment(TextAlignment.CENTER).setFontSize(12f)

        val width = floatArrayOf(100f, 100f)
        val table = Table(width)

        table.setHorizontalAlignment(HorizontalAlignment.CENTER)
        table.addCell(Cell().add(Paragraph("ID Dokumen")))
        table.addCell(Cell().add(Paragraph(id)))
        table.addCell(Cell().add(Paragraph("Nama Calon Interview")))
        table.addCell(Cell().add(Paragraph(nama)))
        table.addCell(Cell().add(Paragraph("Nama Perusahaan")))
        table.addCell(Cell().add(Paragraph(perusahaan)))
        table.addCell(Cell().add(Paragraph("Role")))
        table.addCell(Cell().add(Paragraph(role)))
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        table.addCell(Cell().add(Paragraph("Tanggal Pembuatan PDF")))
        table.addCell(Cell().add(Paragraph(LocalDate.now().format(dateTimeFormatter))))
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a")
        table.addCell(Cell().add(Paragraph("Waktu Pembuatan PDF")))
        table.addCell(Cell().add(Paragraph(LocalTime.now().format(timeFormatter))))

        val barcodeQRCode = BarcodeQRCode("""
            Id Dokumen : $id
            Nama Calon Interview : $nama
            Nama Perusahaan : $perusahaan
            Role : $role
            ${LocalDate.now().format(dateTimeFormatter)}
            ${LocalTime.now().format(timeFormatter)}
        """.trimIndent())

        val qrCodeObject = barcodeQRCode.createFormXObject(ColorConstants.BLACK, pdfDocument)
        val qrCodeImage = Image(qrCodeObject).setWidth(80f).setHorizontalAlignment(HorizontalAlignment.CENTER)
        document.add(image)
        document.add(namapengguna)
        document.add(group)
        document.add(table)
        document.add(qrCodeImage)
        document.close()
        FancyToast.makeText(applicationContext, "PDF Created", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
    }
}