export class Calendar {

    constructor() {
        this.calendar = document.getElementById("calendar_value");

        this.selectYear = document.getElementById("cal-year");
        this.selectMonth = document.getElementById("cal-month");

        const now = new Date();

        this.years = this.range(now.getFullYear(), now.getFullYear() + 1);
        this.months = this.range(1, 12);

        this.valueAppendChildTo(this.selectYear, this.years);
        this.valueAppendChildTo(this.selectMonth, this.months);

        this.selectYear.addEventListener('change', this.change.bind(this));
        this.selectMonth.addEventListener('change', this.change.bind(this));
        this.createCalendar(now.getFullYear(), now.getMonth() + 1);
    }

    range(start, end) {
        return Array(end - start + 1).fill(start).map((x, y) => x + y);
    }

    valueAppendChildTo(target, values) {
        target.options.length = 1;
        for (const value in values) {
            if(values.hasOwnProperty(value)) {
                const option = document.createElement("option");
                option.text = values[value].toString();
                option.value = values[value].toString();
                target.appendChild(option)
            }
        }
    }

    change() {
        console.log(this.selectMonth.value);
        if (this.selectYear.value !== "" && this.selectMonth.value !== "") {
            this.changeCalendar(this.selectYear.value, this.selectMonth.value)
        }
    }

    changeCalendar(year, month) {
        this.calendar.innerHTML = "";
        this.createCalendar(year, month)
    }

    createCalendar(year, month) {
        const date = new Date(year, month - 1, 1);
        const lastDay = new Date(year, month, 0);
        const total = lastDay.getDate() + date.getDay() + (6 - lastDay.getDay());

        let day = 1;
        let row = document.createElement("div");
        row.classList.add("cal-row");

        for (let i = 1; i <= total; i++) {
            if (i % 7 === 1 && i !== 1) {
                this.calendar.appendChild(row);
                row = document.createElement("div");
                row.classList.add("cal-row");
            }

            const col = document.createElement("div");
            col.classList.add("cal-col");
            if (i % 7 === 0) {
                col.classList.add("w3-blue");
            } else if (i % 7 === 1) {
                col.classList.add("w3-red");
            }

            const p = document.createElement("p");
            p.innerHTML = (i <= date.getDay() || day > lastDay.getDate()) ? `&nbsp;` : `${day++}`;
            p.classList.add("cal-day");

            const weather = document.createElement("div");
            weather.classList.add("cal-weather");

            if (p.innerHTML !== `&nbsp;`) {
                weather.innerHTML = "❔";
            }
            // 날씨 이모지 : ☀️🌥️🌦️🌪️🌫️☂️⚡⛄

            col.appendChild(p);
            col.appendChild(weather);
            row.appendChild(col);
        }
        this.calendar.appendChild(row);
    }

}