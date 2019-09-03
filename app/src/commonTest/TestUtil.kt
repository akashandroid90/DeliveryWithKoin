import app.delivery.model.DeliveriesData
import com.google.gson.Gson
import java.io.BufferedReader

object TestUtil {

    fun getDataAsString(): String {
        val inputStream =
            javaClass.classLoader?.getResourceAsStream("api-responses/responsedata.json")
        val reader = BufferedReader(inputStream?.reader())
        val content = StringBuilder()
        reader.use {
            var line = reader.readLine()
            while (line != null) {
                content.append(line)
                line = reader.readLine()
            }
        }
        return content.toString()
    }

    fun getData(fromIndex: Int, toIndex: Int): List<DeliveriesData> {
        val value = getDataAsString()
        val asList = Gson().fromJson(value, Array<DeliveriesData>::class.java).asList()
        if (fromIndex >= asList.size)
            return emptyList()
        return asList.subList(fromIndex, toIndex)
    }

    fun getRandomData(): DeliveriesData {
        val value = getDataAsString()
        val asList = Gson().fromJson(value, Array<DeliveriesData>::class.java).asList()
        return asList.shuffled()[0]
    }
}