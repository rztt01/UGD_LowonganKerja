package room2

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lat_ugd1.EditActivity2
import com.example.lat_ugd1.FragmentNotification

@Database(
    entities = [Note::class],
    version = 1
)

abstract class NoteDB: RoomDatabase() {

    abstract fun noteDao() : NoteDao

    companion object {

        @Volatile private var instance : NoteDB? = null
        private val LOCK = Any()

        operator fun invoke(context: FragmentActivity) = instance ?:
synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
Room.databaseBuilder(
            context.applicationContext,
            NoteDB::class.java,
            "note12345.db"
        ).build()
    }
}