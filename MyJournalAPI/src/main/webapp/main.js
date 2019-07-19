/**
 * The screen shown before the app starts, prompting for username.
 */
Vue.component('login-screen', {
    data() {
        return {
            username: undefined,
            password: undefined,
            newUsername: undefined,
            newPassword: undefined,
            errorMessage: "",
        }
    },
    template: `
        <div>
            <div class="grid-container-login">
                <div class="login-container">
                    </br></br> <input placeholder="Username" v-model="username" />
                    </br> <input type="password" placeholder="Password" v-model="password" />
                    </br> <label class="form-check-label"><input type="checkbox"> Remember me</label>
                    </br> {{ errorMessage }}
                    </br> <button class="btn btn-info" v-on:click="confirmUser">Sign in</button>
                </div>
                <div class="register-container">
                    <div>
                      </br></br> <input placeholder="New username" v-model="newUsername" />
                      </br> <input type="password" placeholder="Password" v-model="newPassword" />
                      </br> <button class="btn btn-info" v-on:click="registerUser">Register</button>
                    </div>
                </div>
            </div>
        </div>
    `,
    methods: {
        confirmUser() {
            if (!this.username) {
                this.errorMessage = "Username is required";
                return;
            }
            if (!this.password) {
              this.errorMessage = "Password is required";
              return;
            }
            this.errorMessage = "";
            this.$emit('user-confirmed', this.username, this.password);
        },
        registerUser() {
            this.$emit('user-registration-confirmed', this.newUsername, this.newPassword);
        }
    }
});

const navigationScreen = Vue.component('navigation-screen', {
    props: ['userData'],
    template: `
        <div>
          <nav class="navbar navbar-expand-sm navbar-lighr bg-light flex-nowrap">
              <span class="navbar-text w-100">Signed in as: {{userData.username}}</span>
              <div class="navbar-collapse collapse w-100 justify-content-center">
                  <ul class="navbar-nav mx-auto">
                      <li class="nav-item">
                          <router-link to="/journal">Journal</router-link>
                      </li>
                      <li class="nav-item">
                          <router-link to="/scrumboard">Scrumboard</router-link>
                      </li>
                      <li class="nav-item">
                          <router-link to="/planningpoker">Planningpoker</router-link>
                      </li>
                  </ul>
              </div>
              <div class="w-100"><!--spacer--></div>
          </nav>
          <router-view v-bind:user-data="userData"> </router-view>
        </div>
    `,
});

const journalScreen = Vue.component('journal-screen', {
  props: ['userData'],
  data: function() {
    return {
      notes: "",
      newNote: "",
      tasks: [],
      newTask: "",
      dayRating: 50,
      monthsInYear: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
      daysInWeek: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      inputDay: undefined,
    }
  },
  template: `
      <div>
          <h1> {{userData}} </h1>
          <div class="header">
              <h1> JOURNAL </h1>
              Today: {{ currentDate }}
          </div>

          <div class="grid-container">

              <div class="task-list">

                  <div class="task-list-header">
                      <h3> TO-DO-LIST </h3>
                      <div class="input-group mb-3">
                          <input v-model="newTask" type="text" class="form-control" placeholder="TO DO ..." >
                          <div class="input-group-append">
                              <button v-on:click="addTask" class="btn btn-info" type="button">Add</button>
                          </div>
                      </div>
                  </div>

                  <ul class="list-group list-group-hover" v-for="(task, index) in tasks">
                      <li class="list-group-item d-flex justify-content-between">
                          <label class="checkbox-container">
                              <input type="checkbox">
                              <span class="checkmark"></span>
                          </label>
                          <span>{{task}}</span>
                          <button class="trash-button" v-on:click="removeTask(index)"><i class="fa fa-trash"></i></button>
                      </li>
                  </ul>
              </div>

              <div class="notes paper">
                <div class="d-flex justify-content-between">
                      <h3> NOTES </h3>
                      <button class="trash-button" v-on:click="removeNotes"><i class="fa fa-trash"></i></button>
                  </div>
                  <div class=notes-content> {{notes}} </div>
                  <div>
                      <textarea class="form-control" v-model="newNote" rows="1"></textarea>
                      <button v-on:click="addNote" type="button" class="btn btn-info btn-block"> Add Note </button>
                  </div>
              </div>

              <div class="day-rater">
                  <h3> RATE YOUR DAY: {{dayRating}} </h3>
                  <div class="slidecontainer">
                      <input v-model="dayRating" type="range" min="1" max="100" value="dayRating" class="slider">
                  </div>
                  <div class="iconbar d-flex justify-content-between">
                      <div class="icon p-2"><i class='far fa-sad-cry'></i></div>
                      <div class="icon p-2"><i class='far fa-frown'></i></div>
                      <div class="icon p-2"><i class='far fa-meh'></i></div>
                      <div class="icon p-2"><i class='far fa-smile'></i></div>
                      <div class="icon p-2"><i class='far fa-smile-beam'></i></div>
                  </div>
              </div>

              <div class="calendar-container">
                  <h3> CALENDAR </h3>
                  <table class="table week-calendar">
                      <thead class="calendar-header">
                        <th v-for="day in daysInWeek">{{day}}</th>
                      </thead>
                      <tbody>
                        <tr class="days-in-week">
                          <td v-for="dayInWeek in daysInCurrentWeek" v-on:click="clickedOnDay(dayInWeek)"> {{dayInWeek}} </td>
                        </tr>
                      </tbody>
                  </table>
                  <p> Date selected: {{inputDay}} </p>
              </div>
          </div>
      </div>
  `,
  computed: {
    currentDate() {
      const today = new Date();
      const dd = String(today.getDate()).padStart(2, '0');
      const mm = this.monthsInYear[parseInt(String(today.getMonth() + 1).padStart(2, '0')) - 1];
      const yyyy = today.getFullYear();

      const todaysDate = dd + ' ' + mm + ' ' + yyyy;
      this.inputDay = todaysDate;
      return todaysDate;
    },
    daysInCurrentWeek() {
        let week = [];
        let current = new Date;
        for (let i = 1; i <= 7; i++) {
          let firstDay = current.getDate()  - current.getDay() + i;
          let dayInWeek = new Date(current.setDate(firstDay)).toISOString().slice(8,10);
          let monthIndex = parseInt(new Date(current.setDate(firstDay)).toISOString().slice(6,8));
          let year = new Date().getFullYear();
          week.push(dayInWeek + " " + this.monthsInYear[monthIndex - 1] + " " + year);
        }
        return week;
    },
  },
  created() {
    this.currentDate;
  },
  methods: {
    addNote() {
        this.notes += this.newNote + ' ';
        this.newNote = "";
    },
    removeNotes() {
        this.notes = "";
    },
    addTask() {
        this.tasks.push(this.newTask);
        this.newTask = "";
    },
    removeTask(index) {
        this.tasks.splice(index,1);
    },
    clickedOnDay(dayInWeek) {
        this.inputDay = dayInWeek;
    },
    async getMyDayJournal(userData, inputDay) {
        const response = await fetch('api/myday', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ user: this.userData , date: inputDay })
        })
    }
  },
  beforeMount() {
    this.getMyDayJournal();
  },
});

const scrumBoard = Vue.component('scumboard-screen', {
  data: function() {
    return {
      scrumBoards: [],
      newBoardForm: undefined,
      projectName: "",
      userStories: [],
      userStory: "",
      addedStories: "",

      boardStatus: ["BACKLOG", "TO-DO", "IN PROGRESS", "DONE"],
    }
  },
  template: `
      <div>
          <div class="header">
              <h1> SCRUMBOARD </h1>
              <button v-on:click="createBoard" class="btn btn-info">Create New Project</button>
          </div>
          <div v-if="newBoardForm" class="new-scrumboard-form">
              <form>
                  <div class="form-group">
                      <label for="projectName">Projectname</label>
                      <input v-model="projectName" class="form-control" placeholder="Enter your new project name ...">
                  </div>

                  <div class="form-group">
                      <label for="userstories">Userstories</label>
                      <div class="input-group mb-3">
                          <input v-model="userStory" class="form-control" placeholder="Enter a userstory..."">
                          <div class="input-group-append">
                              <button v-on:click="addStory" class="btn btn-info"> Add </button>
                          </div>
                      </div>
                  </div>

                  <div>
                    Added Stories: {{addedStories}}
                  </div>

              </form>
              <div class="save-button">
                <button v-on:click="saveNewProject" class="btn btn-info btn-block"> Save </button>
              </div>
          </div>



          <div v-if="!(Object.keys(scrumBoards).length === 0)">
              <div v-for="board in scrumBoards">
                  <h3> PROJECT: {{Object.keys(board)[0]}} </h3>
                  <table class="table scrumboard">
                      <thead>
                        <th v-for="status in boardStatus">{{status}}</th>
                      </thead>
                      <tbody>
                          <tr>
                              <td>
                                <div v-for="postIt in board[Object.keys(board)[0]]" class=post-it> {{postIt}} </div>
                              </td>
                              <td> </td>
                              <td> </td>
                              <td> </td>
                          </tr>
                      </tbody>
                  </table>
                </div>
          </div>
      </div>

  `,
  methods: {
    createBoard() {
        this.newBoardForm = [];
    },
    saveNewProject() {
        let boardObj = {};
        boardObj[this.projectName] = this.userStories
        this.scrumBoards.push(boardObj);
        this.projectName = "";

        this.newBoardForm = undefined;
        this.userStories = [];
        this.addedStories = "";
        boardObj = {};

    },
    addStory() {
      this.userStories.push(this.userStory);
      this.addedStories += this.userStory + ". ";
      this.userStory = "";
    },
  },
});

const planningPoker = Vue.component('planningpoker-screen', {
  data: function () {
    return {
      startingGameForm: undefined,
    }
  },
  template: `
      <div>
          <div class="header">
              <h1> PLANNING POKER </h1>
          </div>
          <button v-on:click="startGame" class="btn btn-info">Start Game</button>
          <div v-if="startingGameForm">
              <form>
                  <div class="form-group">
                      <label for="projectName">Players</label>
                      <input class="form-control" placeholder="Enter the number of players ...">
                  </div>
              </form>
          </div>
      </div>
  `,
  methods: {
    startGame() {
      this.startingGameForm = "";
    }
  },
});

const routes = [
  {path: '/journal', component: journalScreen},
  {path: '/scrumboard', component: scrumBoard},
  {path: '/planningpoker', component: planningPoker}
]

const router = new VueRouter({
  routes
})

const app = new Vue({
    router,
    el: '#app',
    data: {
        userData: undefined,
    },
    computed: {
        userConfirmed() {
            return this.userData != undefined;
        },
    },
    methods: {
        async loggingIn(username, password) {
            router.push('journal')
            const response = await fetch('api/users', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username: username , password: password })
            })
            this.userData = await response.json();
            if (!this.userData) {
                alert("Username and password do not match");
            }
        },
        async registerUser(username, password) {
            router.push('journal')
            const response = await fetch('api/register', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username: username , password: password })
            })
            this.userData = await response.json();
      }
    }
}).$mount('#app');
