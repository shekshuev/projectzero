using System;

namespace surveyclient.Models
{
    public class Answer
    {
        /// <summary>
        /// Текст ответа
        /// </summary>
        public string Text { get; set; }

        /// <summary>
        /// Код вопроса, для пользователя скрыт
        /// </summary>
        public int Code { get; set; }

        /// <summary>
        /// Либо true, если вопрос выбран, либо false, если не выбран,
        /// либо null, когда анкета только пришла по сети
        /// Использовать при <see cref="Question.Type"/> single или multiple,
        /// иначе оставить null
        /// </summary>
        public bool? Selected { get; set; }

        /// <summary>
        /// Текст ответа опрашиваемого.
        /// Использовать при <see cref="Question.Type"/> open,
        /// иначе оставить null
        /// </summary>
        public String TypedText { get; set; }
    }
}

