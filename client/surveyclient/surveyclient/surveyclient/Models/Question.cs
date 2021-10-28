using System;
using System.Collections.Generic;

namespace surveyclient.Models
{
    public class Question
    {
        /// <summary>
        /// Текст вопроса
        /// </summary>
        public string Title { get; set; }

        /// <summary>
        /// open - открытый с вводом текста,
        /// single - один ответ,
        /// multiple - два ответа
        /// </summary>
        public string Type { get; set; }

        /// <summary>
        /// Список ответов
        /// </summary>
        public List<Answer> Answers { get; set; }

        /// <summary>
        /// Обязательный или нет вопрос
        /// </summary>
        public bool Required { get; set; }

    }
}

