using System;

namespace surveyclient.Models
{
    public class Survey : BaseModel
    {
        /// <summary>
        /// Дата создания опроса. Для пользователя скрыто
        /// </summary>
        public DateTime CreatedAt { get; set; }

        /// <summary>
        /// Дата начала опроса. Для пользователя скрыто.
        /// Должно быть выставлено при начале опроса
        /// </summary>
        public DateTime? BeginDate { get; set; }

        /// <summary>
        /// Дата окончания опроса. Для пользователя скрыто
        /// Должно быть выставлено при завершении опроса
        /// </summary>
        public DateTime? EndDate { get; set; }
    }
}

