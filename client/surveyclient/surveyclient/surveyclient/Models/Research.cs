using System;
using System.Collections.Generic;

namespace surveyclient.Models
{
    public class Research : BaseModel
    {
        /// <summary>
        /// Дата создания исследования. Для пользователя скрыто
        /// </summary>
        public DateTime CreatedAt { get; set; }

        /// <summary>
        /// Дата начала исследования. Для пользователя скрыто
        /// </summary>
        public DateTime BeginDate { get; set; }

        /// <summary>
        /// Дата окончания исследования. Для пользователя скрыто
        /// </summary>
        public DateTime EndDate { get; set; }

        /// <summary>
        /// Название исследования.
        /// </summary>
        public string Title { get; set; }

        /// <summary>
        /// Описание исследования.
        /// </summary>
        public string Description { get; set; }

        /// <summary>
        /// Список уникальных опросов.
        /// </summary>
        public List<Survey> Surveys { get; set; }
    }
}

