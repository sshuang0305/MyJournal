/**
 * The screen shown before the app starts to login or register an user.
 */
Vue.component('start-screen', {
    data() {
        return {
            loginName: undefined,
            loginPassword: undefined,
            registerName: undefined,
            registerPassword1: undefined,
            registerPassword2: undefined,
        }
    },
    template: `
        <div>
            <div class="startscreen-container">
                <h4> Login </h4>
                <div> <input placeholder="Username" v-model="loginName"/> </div>
                <div> <input type="password" placeholder="Password" v-model="loginPassword"/> </div>
                <button class="btn btn-info" @click="logInUser">Log in</button>
            </div>
            <div class="startscreen-container">
                <h4> Register </h4>
                <div> <input placeholder="Username" v-model="registerName"/> </div>
                <div> <input type="password" placeholder="Password" v-model="registerPassword1"/> </div>
                <div> <input type="password" placeholder="Repeat password" v-model="registerPassword2"/> </div>
                <button class="btn btn-info" @click="registerUser">Register</button>
            </div>
        </div>
    `,
    methods: {
        logInUser() {
            if (!this.loginName || !this.loginPassword) {
                alert("Username and password do not match.");
                return;
            }
            this.$emit('user-logged-in', this.loginName, this.loginPassword);
        },
        registerUser() {
            if (!this.registerName || !this.registerPassword1 || !this.registerPassword2 ||
                (this.registerPassword1 != this.registerPassword2)) {
                alert("Invalid entry. Please try again.");
                return;
            }
            this.$emit('user-registered', this.registerName, this.registerPassword1);
        }
    }
});


/**
 * Navigation bar with links to the journal or scrumboard.
 */
const navigationBar = Vue.component('navigation-bar', {
    props: ['userData'],
    template: `
        <div>
            <nav class="navbar navbar-expand-sm navbar-lighr bg-light">
                <span class="navbar-text w-100">Signed in as: {{userData.username}} </span>
                    <ul class="navbar-nav mx-auto">
                        <li class="nav-item">
                            <router-link to="/journal"> Journal </router-link>
                        </li>
                        <li class="nav-item">
                            <router-link to="/scrumboard"> Scrumboard </router-link>
                        </li>
                    </ul>
                <div class="w-100"><!--spacer--></div>
            </nav>
            <router-view v-bind:user-data="userData"></router-view>
        </div>
    `,
});

/**
 * Journal page with a to-do-list, notes, dayrating, and a calendar.
 */
const journalScreen = Vue.component('journal-screen', {
  props: ['userData'],
  data: function() {
    return {
      myDay: undefined,
      tasks: [],
      addedTask: "",
      notes: [],
      addedNote: "",
      dayRating: undefined,
      daysInWeek: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      selectedDay: undefined,
      selectedWeek: undefined,
    }
  },
  template: `
      <div class="header">
          <h1> JOURNAL </h1>
          Date selected: {{ selectedDay }}

          <div class="grid-container-journal">
              <div class="task-list">
                  <h3> TO-DO-LIST </h3>
                  <div class="input-group mb-3">
                      <input v-model="addedTask" type="text" class="form-control" placeholder="TO DO ..." >
                      <button @click="addTask" class="btn btn-info input-group-append" type="button">Add</button>
                  </div>
                  <ul class="list-group list-group-hover" v-for="(task, index) in tasks">
                      <li class="list-group-item d-flex justify-content-between">
                          <label class="checkbox-container">
                              <input type="checkbox">
                              <span class="checkmark"></span>
                          </label>
                          {{task.taskText}}
                          <button class="trash-button" @click="deleteTask(task, index)"><i class="fa fa-trash"></i></button>
                      </li>
                  </ul>
              </div>

              <div class="notes">
                  <h3 class="header"> NOTES </h3>
                  <ul v-for="(note, index) in notes">
                      <li class= "list-group-hover d-flex justify-content-between">
                          {{note.noteText}}
                          <button class="trash-button" @click="deleteNote(note, index)"><i class="fa fa-trash"></i></button>
                       </li>
                  </ul>
                  <textarea class="form-control" v-model="addedNote" rows="3"></textarea>
                  <button @click="addNotes" type="button" class="btn btn-info btn-block"> Add Notes </button>
              </div>

              <div class="day-rater">
                  <h3> RATE YOUR DAY: {{dayRating}} </h3>
                      <input v-model="dayRating" type="range" min="1" max="100" value="dayRating" class="slider">
                  <div class="iconbar d-flex justify-content-between">
                      <div class="icon p-2"><i class='far fa-sad-cry'></i></div>
                      <div class="icon p-2"><i class='far fa-frown'></i></div>
                      <div class="icon p-2"><i class='far fa-meh'></i></div>
                      <div class="icon p-2"><i class='far fa-smile'></i></div>
                      <div class="icon p-2"><i class='far fa-smile-beam'></i></div>
                  </div>
                  <button v-on:click="saveRating" type="button" class="btn btn-info btn-lg"> Save </button>
              </div>

              <div class="calendar-container">
                  <button @click="previousWeek" type="button" class="btn btn-info round pull-left"> < </button>
                  <button @click="nextWeek" type="button" class="btn btn-info round pull-right"> > </button>
                  <h3> CALENDAR </h3>
                  <table class="table week-calendar">
                      <thead class="header"> <th v-for="day in daysInWeek"> {{day}} </th> </thead>
                      <tbody>
                          <tr class="days-in-week"> <td v-for="dayInWeek in selectedWeek"  @click="clickedOnDay(dayInWeek)"> {{dayInWeek}} </td> </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
  `,
  methods: {
      async addTask() {
          const response = await fetch('api/journal/addtask', {
              method: 'PUT',
              headers: {
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify({dayID: this.myDay.dayID, task: this.addedTask})
          })
          this.tasks.push({"taskText": this.addedTask});
          this.addedTask = "";
      },
      async deleteTask(task, index) {
          const response = await fetch('api/journal/deletetask', {
              method: 'PUT',
              headers: {
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify({taskID: task.taskID})
          })
          this.tasks.splice(index, 1);
      },
      async addNotes() {
          const response = await fetch('api/journal/addnotes', {
              method: 'PUT',
              headers: {
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify({dayID: this.myDay.dayID, note: this.addedNote})
          })
          this.notes.push({"noteText" : this.addedNote});
          this.addedNote = "";
      },
      async deleteNote(note, index) {
          const response = await fetch('api/journal/deletenote', {
              method: 'PUT',
              headers: {
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify({dayID: this.myDay.dayID, noteID: note.noteID})
          })
          this.notes.splice(index, 1);
      },
      async saveRating() {
          const response = await fetch('api/journal/saverating', {
              method: 'PUT',
              headers: {
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify({dayID: this.myDay.dayID, dayRating: this.dayRating})
          })
      },
      clickedOnDay(dayInWeek) {
          this.selectedDay = dayInWeek;
          this.getMyDayJournal();
      },
      async getMyDayJournal() {
          const response = await fetch('api/journal/myday', {
              method: 'POST',
              headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify({userID: this.userData.userID , date: this.selectedDay })
          })
          this.myDay = await response.json();
          this.notes = this.myDay.notes;
          this.dayRating = this.myDay.dayRating;
          this.tasks = this.myDay.tasks;
      },
      previousWeek() {
          const today = new Date();
          today.setDate(today.getDate() - 7);
          this.weekFormatter(today)
      },
      nextWeek() {
          const today = new Date();
          today.setDate(today.getDate());
          this.weekFormatter(today)
      },
      weekFormatter(day) {
          this.selectedWeek = [];
          for (let i = 1; i <= 7; i++) {
              let firstDay = day.getDate()  - day.getDay() + i;
              let date= new Date(day.setDate(firstDay));
              let dd = date.toISOString().slice(8,10);
              let mm = date.toISOString().slice(5,7);
              let yyyy = date.getFullYear();
              this.selectedWeek.push(dd + '/' + mm + '/' + yyyy);
          }
      }
  },
  beforeMount() {
      const today = new Date();
      this.weekFormatter(today);
      const todaysDayInWeek = new Date().getDay();
      this.selectedDay = this.selectedWeek[todaysDayInWeek - 1];
      this.getMyDayJournal();
  },
});


/**
 * Routes to the journal page and the scrumboard page.
 */
const routes = [{path: '/journal', component: journalScreen}];
const router = new VueRouter({routes});

/**
 * Logs in or registers user and brings user to the journal page..
 */
const app = new Vue({
    router,
    el: '#app',
    data: {
        userData: undefined,
    },
    computed: {
        userLoggedIn() {
            return this.userData != undefined;
        },
    },
    methods: {
        async logInUser(username, password) {
            router.push('journal')
            const response = await fetch('api/users/login', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username: username , password: password })
            })
            if (!response.ok) {
                alert("Username and password do not match.");
            }
            else {
                this.userData = await response.json();
            }
        },
        async registerUser(username, password) {
            router.push('journal')
            const response = await fetch('api/users/register', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({username: username , password: password })
            })
            if (!response.ok) {
                alert("Username already taken.");
            }
            else {
                this.userData = await response.json();
            }
        }
    }
}).$mount('#app');
