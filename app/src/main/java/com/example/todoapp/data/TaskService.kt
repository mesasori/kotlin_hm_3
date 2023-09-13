package com.example.todoapp.data

import com.example.todoapp.data.models.TaskItem
import com.example.todoapp.data.models.Urgency
import java.util.HashMap

class TaskService {
    private val data = HashMap<String, TaskItem>()

    init {
        data["1"] = TaskItem("1", "Простая задачка без дедлайна", Urgency.HIGH, null,false, 1586913715141,"Заглушка")
        data["2"] = TaskItem("2", "Срочная задачка без дедлайна", Urgency.HIGH, null, true,1586913715141,"Заглушка")
        data["3"] = TaskItem("3", "Не обязательная задачка без дедлайна", Urgency.LOW, null, false,1586913715141,"Заглушка")
        data["4"] = TaskItem("4", "Простая задачка с дедлайном", Urgency.NONE, 1585913715141,false, 1586913715141,"Заглушка")
        data["5"] = TaskItem("5", "Срочная задачка с дедлайном", Urgency.HIGH, 1586913715141,false, 1586913715141,"Заглушка")
        data["6"] = TaskItem("6", "Не обязательная задачка с дедлайном", Urgency.LOW, 1584913715141, false,1586913715141,"Заглушка")
        data["7"] = TaskItem("7", "Срочная задачка с дедлайном выполненная", Urgency.LOW, 1584913515141,true, 1586913715141,"Заглушка")
        data["8"] = TaskItem("8", "Обычная задачка без дедлайна выполненная", Urgency.NONE, null,true, 1586913715141,"Заглушка")
        data["9"] = TaskItem("9", "Не обязательная задачка без дедлайна выполненная", Urgency.LOW, null, true,1586913715141,"Заглушка")
        data["10"] = TaskItem("10", "Очень длинная задачка для скролла второго экрана без дедлайна. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.",
            Urgency.LOW, null,false, 1586913715141,"Заглушка")
        data["11"] = TaskItem("11", "Очень длинная задачка для скролла второго экрана с дедлайном. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.",
            Urgency.LOW, 1584913315141,false, 1586913715141,"Заглушка")
        data["12"] = TaskItem("12", "Сварить суп", Urgency.HIGH, null, false,1586913715141,"Заглушка")
        data["13"] = TaskItem("13", "Доделать проект", Urgency.NONE, 1584912315141,false, 1586913515141,"Заглушка")
        data["14"] = TaskItem("14", "Залить на гит этот замечательный to do list", Urgency.HIGH, null,false, 1586913715141,"Заглушка")

        data["15"] = TaskItem("15", "Еще больше супа", Urgency.HIGH, null, false,1586913415141,"Заглушка")
        data["16"] = TaskItem("16", "Доделать задачки в Яндекс контесте", Urgency.LOW, null,false, 1586912715141,"Заглушка")
        data["17"] = TaskItem("17", "Радоваться жизни", Urgency.LOW, 1584913345141,false, 1586913315141,"Заглушка")
    }

    fun getData(): List<TaskItem> {
        return ArrayList(data.values)
    }

    fun addNewTask(task: TaskItem) {
        data[data.size.toString()] = task
    }
}