export class Calendar {
    constructor() {
        this.calendar = document.getElementById("calendar");
        this.calendarValue = document.getElementById("calendar_value");

        this.createCalendar(2021, 4);
    }

    createCalendar(year, month) {
        const date = new Date(year, month - 1, 1);
        const lastDay = new Date(year, month, 0);
        const total = lastDay.getDate() + date.getDay() + (6 - lastDay.getDay());

        let day = 1;
        let row = document.createElement("div");

        for (let i = 1; i <= total; i++) {
            row.classList.add("cal-row");
            if (i % 7 === 1) {
                this.calendarValue.appendChild(row);
                row = document.createElement("div");
            }

            const col = document.createElement("span");
            col.classList.add("cal_col");
            if (i % 7 === 0) {
                col.classList.add("w3-blue");
            } else if (i % 7 === 1) {
                col.classList.add("w3-red");
            }
            const p = document.createElement("p");

            p.innerHTML = (i <= date.getDay() || day > lastDay.getDate()) ? `&nbsp;` : `${day++}`;
            col.appendChild(p);
            row.appendChild(col);
        }
    }
}