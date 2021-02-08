package de.co.derakhshan.hamdad.event


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.co.derakhshan.hamdad.Arrange
import de.co.derakhshan.hamdad.DateConvertor
import de.co.derakhshan.hamdad.database.EventDatabase
import de.co.derakhshan.hamdad.database.tabels.EventInfo
import java.text.SimpleDateFormat
import java.util.*


class EventViewModel(val database: EventDatabase) : ViewModel() {

    lateinit var clickListener: MessageTransformer
    private lateinit var model: EventInfo

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _place = MutableLiveData<String>()
    val place: LiveData<String>
        get() = _place

    private val _holder = MutableLiveData<String>()
    val holder: LiveData<String>
        get() = _holder

    private val _time = MutableLiveData<String>()
    val time: LiveData<String>
        get() = _time


    private val _eventDate = MutableLiveData<String>()
    val eventDate: LiveData<String>
        get() = _eventDate


    private val _isParticipate = MutableLiveData<EventInfo>()
    val canEnter: LiveData<EventInfo>
        get() = _isParticipate


    private val _image = MutableLiveData<String>()
    val image: LiveData<String>
        get() = _image

    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description

    fun getInfo(id: String): LiveData<EventInfo> = database.eventDao.getEvent(id)


    fun setInfo(data: EventInfo) {

        model = data

        _image.value = data.cover

        _title.value = data.title
        _eventDate.value = Arrange().dateStringMaker(data.event_date)
        _time.value = data.time
        _place.value = data.place

        _holder.value = data.holder
        _description.value = data.description
        _isParticipate.value = data

    }

    fun buttonListener() {
        val c: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("yyyy-MM-dd",Locale.getDefault())
        val currentDate = DateConvertor().ConvertToPersianDate(df.format(c)).split("-")
        val eventDate = model.event_date.split("-")

        val currentDay = currentDate[2].toInt()
        val currentMonth = currentDate[1].toInt()
        val eventDay = eventDate[2].toInt()
        val eventMonth = eventDate[1].toInt()

        if (model.isDone && !model.isParticipant)
            clickListener.transform("مهلت ثبت نام تمام شده است!")
        else if (!model.isDone && !model.isParticipant)
            clickListener.transform("شرکت در رویداد")
        else if (model.isParticipant && currentMonth > eventMonth)
            clickListener.transform("می توانید در نظر سنجی شرکت کنید")
        else if (model.isParticipant && currentMonth < eventMonth)
            clickListener.transform("رویداد هنوز برگزار نشده است")
        else if (model.isParticipant && currentMonth == eventMonth && currentDay >= eventDay)
            clickListener.transform("می توانید در نظر سنجی شرکت کنید")
        else if (model.isParticipant && currentMonth == eventMonth && currentDay < eventDay)
            clickListener.transform("رویداد هنوز برگزار نشده است")
    }

}