/**
 * The screen shown before the app starts, prompting for username and password.
 */
Vue.component('login-screen', {
    data() {
        return {
            username: undefined,
            password: undefined,
            errorMessage: "",
            newUsername: undefined,
            newPassword: undefined,
            repeatPassword: undefined,
            newErrorMessage: "",
        }
    },
    template: `
        <div class="grid-container-login">
            <div class="login-container">
                    <h4> Login </h4>
                    <div> <input placeholder="Username" v-model="username" /> </div>
                    <div> <input type="password" placeholder="Password" v-model="password" /> </div>
                    <div> <label class="form-check-label"><input type="checkbox"> Remember me</label> </div>
                    <div> {{ errorMessage }} </div>
                    <button class="btn btn-info btn-block" v-on:click="confirmUser">Sign in</button>
            </div>
            <div class="register-container">
                      <h4> Register </h4>
                      <div> <input placeholder="New username" v-model="newUsername" /> </div>
                      <div> <input type="password" placeholder="Password" v-model="newPassword" /> </div>
                      <div> <input type="password" placeholder="Repeat password" v-model="repeatPassword" /> </div>
                      <div> {{ newErrorMessage }} </div>
                      <button class="btn btn-info btn-block" v-on:click="registerUser">Register</button>
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
            if (!this.newUsername) {
                this.newErrorMessage = "Username is required";
                return;
            }
            if (!this.newPassword) {
                this.newErrorMessage = "Password is required";
                return;
            }
            if (this.newPassword != this.repeatPassword) {
                this.newErrorMessage = "Passwords do not match";
                return;
            }
            this.$emit('user-registration-confirmed', this.newUsername, this.newPassword);
        }
    }
});

/**
 * Navigation bar with links to the journal or scrumboard.
 */
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
                    </ul>
                </div>
                <div class="w-100"><!--spacer--></div>
            </nav>
            <router-view v-bind:user-data="userData"> </router-view>
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
      notes: "",
      newNote: "",
      tasks: [],
      newTask: "",
      dayRating: undefined,
      monthsInYear: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
      daysInWeek: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      selectedDay: undefined,
      selectedWeek: undefined,
    }
  },
  template: `
      <div>
          <div class="header">
              <h1> JOURNAL </h1>
              Date selected: {{ selectedDay }}
          </div>

          <div class="grid-container-journal">

              <div class="task-list">
                  <div class="header">
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
                          <button class="trash-button" v-on:click="removeTask(task, index)"><i class="fa fa-trash"></i></button>
                      </li>
                  </ul>
              </div>

              <div class="paper">
                  <div class="header d-flex justify-content-between">
                        <h3> NOTES </h3>
                        <button class="trash-button" v-on:click="removeNotes"><i class="fa fa-trash"></i></button>
                  </div>
                  {{notes}}
                  <textarea class="form-control" v-model="newNote" rows="1"></textarea>
                  <button v-on:click="addNote" type="button" class="btn btn-info btn-block"> Add Note </button>
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
                  <button v-on:click="saveRating" type="button" class="btn btn-info btn-lg"> Save </button>
              </div>

              <div class="calendar-container">
                  <button v-on:click="previousWeek" type="button" class="btn btn-info round pull-left"> < </button>
                  <button v-on:click="nextWeek" type="button" class="btn btn-info round pull-right"> > </button>
                  <h3> CALENDAR </h3>
                  <table class="table week-calendar">
                      <thead class="header">
                          <th v-for="day in daysInWeek">{{day}}</th>
                      </thead>
                      <tbody>
                          <tr class="days-in-week">
                            <td v-for="dayInWeek in selectedWeek" v-on:click="clickedOnDay(dayInWeek)"> {{dayInWeek}} </td>
                        </tr>
                      </tbody>
                  </table>
                   <p> Date selected: {{selectedDay}} </p>
              </div>
          </div>
      </div>
  `,
  methods: {
      async addNote() {
          this.notes += this.newNote + " ";
          const response = await fetch('api/notes', {
              method: 'PUT',
              headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify({userID: this.userData.userID , date: this.selectedDay, notes: this.notes})
          })
          this.newNote = "";
      },
      async removeNotes() {
          this.notes = "";
          const response = await fetch('api/deletenotes', {
              method: 'PUT',
              headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify({userID: this.userData.userID , date: this.selectedDay, notes: this.notes})
          })
          this.notes = this.myDay.notes;
      },
      async addTask() {
          this.tasks.push(this.newTask);
          const response = await fetch('api/tasks', {
              method: 'PUT',
              headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify({userID: this.userData.userID , date: this.selectedDay, task: this.newTask})
          })
          this.tasks = this.myDay.tasks;
          this.newTask = "";
      },
      async removeTask(task, index) {
          this.tasks.splice(index, 1);
          const response = await fetch('api/deletetask', {
              method: 'PUT',
              headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify({userID: this.userData.userID , date: this.selectedDay, task: task})
          })
          this.tasks = this.myDay.tasks;
      },
      clickedOnDay(dayInWeek) {
          this.selectedDay = dayInWeek;
          this.getMyDayJournal();
      },
      async getMyDayJournal() {
          const response = await fetch('api/myday', {
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
      async saveRating() {
        const response = await fetch('api/rating', {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({userID: this.userData.userID , date: this.selectedDay, dayRating: this.dayRating})
        })
      },
      previousWeek() {
          const today = new Date();
          today.setDate(today.getDate() - 7);
          let week = [];
          for (let i = 1; i <= 7; i++) {
              let firstDay = today.getDate()  - today.getDay() + i;
              let dayInWeek = new Date(today.setDate(firstDay)).toISOString().slice(8,10);
              let monthIndex = parseInt(new Date(today.setDate(firstDay)).toISOString().slice(6,8));
              let year = new Date().getFullYear();
              week.push(dayInWeek + " " + this.monthsInYear[monthIndex - 1] + " " + year);
          }
          this.selectedWeek = week;
      },
      nextWeek() {
        const today = new Date();
        today.setDate(today.getDate());
        let week = [];
        for (let i = 1; i <= 7; i++) {
            let firstDay = today.getDate()  - today.getDay() + i;
            let dayInWeek = new Date(today.setDate(firstDay)).toISOString().slice(8,10);
            let monthIndex = parseInt(new Date(today.setDate(firstDay)).toISOString().slice(6,8));
            let year = new Date().getFullYear();
            week.push(dayInWeek + " " + this.monthsInYear[monthIndex - 1] + " " + year);
        }
        this.selectedWeek = week;
      }
  },
  beforeMount() {
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = this.monthsInYear[parseInt(String(today.getMonth() + 1).padStart(2, '0')) - 1];
    const yyyy = today.getFullYear();
    const todaysDate = dd + ' ' + mm + ' ' + yyyy;
    this.selectedDay = todaysDate;
    this.getMyDayJournal();
    let week = [];
    for (let i = 1; i <= 7; i++) {
        let firstDay = today.getDate()  - today.getDay() + i;
        let dayInWeek = new Date(today.setDate(firstDay)).toISOString().slice(8,10);
        let monthIndex = parseInt(new Date(today.setDate(firstDay)).toISOString().slice(6,8));
        let year = new Date().getFullYear();
        week.push(dayInWeek + " " + this.monthsInYear[monthIndex - 1] + " " + year);
    }
    this.selectedWeek = week;
  },
});

/**
 * Scrumboard page with your projects.
 */
const scrumBoard = Vue.component('scumboard-screen', {
  props: ['userData'],
  data: function() {
    return {
      scrumBoards: [],
      board: {projectName: "", backlog: [], todo: [], inprogress: [], done: []},
      newBoardForm: undefined,
      userStory: "",
      addedStories: "",
      boardColumns: ["BACKLOG", "TO-DO", "IN PROGRESS", "DONE"],
      newMember: "",
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
                      <input v-model="board.projectName" class="form-control" placeholder="Enter your new project name ...">
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
              <div v-for="scrumBoard in scrumBoards">
                  <div class="scrumboard">
                      <nav class="navbar navbar-expand-lg navbar-dark bg-info">
                          <h3 class="navbar-brand"> PROJECT: {{scrumBoard.projectName}} </h3>
                          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                              <span class="navbar-toggler-icon"></span>
                          </button>
                          <div class="collapse navbar-collapse">
                              <ul class="navbar-nav mr-auto">
                                  <li class="nav-item">
                                      <button @click="deleteProject(scrumBoard)" class="btn btn-light navbutton">Delete Project</button>
                                  </li>
                              </ul>
                              <form class="form-inline my-2 my-lg-0">
                                  <input v-model="newMember" class="form-control mr-sm-2" placeholder="Username">
                                  <button @click="addMemberToProject(scrumBoard)" class="btn btn-light my-2 my-sm-0"> Add member </button>
                              </form>
                          </div>
                      </nav>

                      <table class="table">
                          <thead>
                            <th v-for="boardColumn in boardColumns">{{boardColumn}}</th>
                          </thead>
                          <tbody>
                              <tr>
                                  <td>
                                      <ul class="list-group list-group-hover" v-for="(postIt, index) in scrumBoard.backlog">
                                          <li v-on:click="moveToToDo(scrumBoard, postIt, index)" class="list-group-item d-flex justify-content-between post-it">
                                              {{postIt}}
                                          </li>
                                      </ul>
                                  </td>
                                  <td>
                                      <ul class="list-group list-group-hover" v-for="(postIt, index) in scrumBoard.todo">
                                          <li v-on:click="moveToInProgress(scrumBoard, postIt, index)" class="list-group-item d-flex justify-content-between post-it">
                                              {{postIt}}
                                          </li>
                                      </ul>
                                  </td>
                                  <td>
                                      <ul class="list-group list-group-hover" v-for="(postIt, index) in scrumBoard.inprogress">
                                          <li v-on:click="moveToDone(scrumBoard, postIt, index)" class="list-group-item d-flex justify-content-between post-it">
                                              {{postIt}}
                                          </li>
                                      </ul>
                                  </td>
                                  <td>
                                      <ul class="list-group list-group-hover" v-for="(postIt, index) in scrumBoard.done">
                                          <li v-on:click="moveToBacklog(scrumBoard, postIt, index)" class="list-group-item d-flex justify-content-between post-it">
                                              {{postIt}}
                                          </li>
                                      </ul>
                                  </td>
                              </tr>
                          </tbody>
                      </table>
                      <button v-on:click="saveChangesProject(scrumBoard)" class="btn btn-info btn-block"> Save Changes In Project </button>
                    </div>
                </div>
          </div>
      </div>

  `,
  methods: {
    createBoard() {
        this.newBoardForm = [];
    },
    async saveNewProject() {
        this.newBoardForm = undefined;
        this.addedStories = "";

        const response = await fetch('api/savescrumboard', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({userID: this.userData.userID , projectName: this.board.projectName, stories: this.board.backlog})
        })

        if (!response.ok) {
          alert("not valid project name");
        }
        else {
            let scrumboardObj = await response.json();
            let scrumboard = {scrumboardID: scrumboardObj.scrumboardID, projectName: scrumboardObj.projectName, backlog: scrumboardObj.backlog, todo: scrumboardObj.todo, inprogress: scrumboardObj.inprogress, done: scrumboardObj.done, userID: scrumboardObj.userID};

            const response2 = await fetch('api/saveuserandboardlinker', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({userID: scrumboard.userID , scrumboardID: scrumboard.scrumboardID})
            })
        }
        this.board = {projectName: "", backlog: [], todo: [], inprogress: [], done: []};
        this.getMyScrumboards();
    },
    addStory() {
      this.board.backlog.push(this.userStory);
      this.addedStories += this.userStory + ". ";
      this.userStory = "";
    },
    addNewStory() {

    },
    moveToToDo(scrumBoard, postIt, index) {
      scrumBoard.todo.push(postIt);
      scrumBoard.backlog.splice(index, 1);
    },
    moveToInProgress(scrumBoard, postIt, index) {
      scrumBoard.inprogress.push(postIt);
      scrumBoard.todo.splice(index, 1);
    },
    moveToDone(scrumBoard, postIt, index) {
      scrumBoard.done.push(postIt);
      scrumBoard.inprogress.splice(index, 1);
    },
    moveToBacklog(scrumBoard, postIt, index) {
      scrumBoard.backlog.push(postIt);
      scrumBoard.done.splice(index, 1);
    },
    async getMyScrumboards() {
        const response = await fetch('api/myscrumboards', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({userID: this.userData.userID})
        });
        let boards = await response.json();
        this.scrumBoards = boards.scrumboards;
      },
      async saveChangesProject(scrumBoard) {
        const response = await fetch('api/savechangesproject', {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({scrumboardID: scrumBoard.scrumboardID, backlog: scrumBoard.backlog, todo: scrumBoard.todo, inprogress: scrumBoard.inprogress, done: scrumBoard.done})
        })
        this.getMyScrumboards();
      },
      async addMemberToProject(scrumBoard) {
        const response = await fetch('api/addnewmember', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username: this.newMember, scrumboardID: scrumBoard.scrumboardID})
        })
        if (!response.ok) {
          alert("User does not exists");
        }
        else {
          alert(this.newMember + " added to: " + scrumBoard.projectName);
        }
        this.newMember = "";
      },
      async deleteProject(scrumBoard) {
          const response = await fetch('api/deleteprojectforeveryone', {
              method: 'DELETE',
              headers: {
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify({userID: this.userData.userID, scrumboardID: scrumBoard.scrumboardID})
          })
          if (!response.ok) {
            alert(scrumBoard.projectName + " is removed from your space.");
          }
          else {
            alert(scrumBoard.projectName + " is removed from you and your team members space");
          }
          this.getMyScrumboards();
      }
  },
  beforeMount() {
    this.getMyScrumboards();
  }
});

const routes = [
  {path: '/journal', component: journalScreen},
  {path: '/scrumboard', component: scrumBoard},
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
            if (!response.ok) {
              alert("Username and password do not match.");
            }
            else {
                this.userData = await response.json();
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
