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
 * Scrumboard page with your projects.
 */
const scrumBoard = Vue.component('scumboard-screen', {
    props: ['userData'],
    data: function() {
      return {
        scrumBoards: [],
        board: {projectName: "", stories: {backlog: [], todo: [], inprogress: [], done: []}},
        newBoardForm: undefined,
        userStory: "",
        addedStories: "",
        boardColumns: ["BACKLOG", "TODO", "INPROGRESS", "DONE"],
        newMember: "",
      }
    },
    template: `
        <div>
            <div class="header">
                <h1> SCRUMBOARD </h1>
                <button @click="createBoard" class="btn btn-info">Create New Project</button>
            </div>

            <form v-if="newBoardForm" class="new-scrumboard-form">
                <div class="form-group">
                    <label for="projectName">Projectname</label>
                    <input v-model="board.projectName" class="form-control" placeholder="Enter your new project name ...">
                </div>
                <div class="form-group">
                    <label for="userstories"> Userstories </label>
                    <div class="input-group mb-3">
                        <input v-model="userStory" class="form-control" placeholder="Enter a userstory..."">
                        <button @click="addStory" class="btn btn-info input-group-append"> Add </button>
                    </div>
                </div>
                Added Stories: {{addedStories}}
                <button @click="saveNewProject" class="btn btn-info btn-block"> Save </button>
            </form>

            <div v-for="scrumBoard in scrumBoards">
                <div class="scrumboard">
                    <nav class="navbar navbar-expand-lg navbar-dark bg-info">
                        <h3 class="navbar-brand"> PROJECT: {{scrumBoard.projectName}} </h3>
                        <ul class="navbar-nav mr-auto">
                            <li class="nav-item"> <button @click="deleteProject(scrumBoard)" class="btn btn-light navbutton">Delete Project</button> </li>
                        </ul>
                        <form class="form-inline my-2 my-lg-0">
                            <input v-model="newMember" class="form-control mr-sm-2" placeholder="Username">
                            <button @click="addMemberToProject(scrumBoard)" class="btn btn-light my-2 my-sm-0"> Add member </button>
                        </form>
                    </nav>

                    <table class="scrumboard-table table">
                        <thead> <th v-for="boardColumn in boardColumns"> {{boardColumn}} </th> </thead>
                        <tbody>
                            <tr>
                                <td  v-for="(column, columnindex) in scrumBoard.userStories">
                                    <ul v-for="(postIt, index) in column">
                                        <li class="list-group list-group-hover">
                                            <div class="post-it-container d-flex justify-content-start">
                                                <button @click="movePostItToPrevious(scrumBoard, columnindex, postIt, index)" type="button" class="btn btn-info"> < </button>
                                                <p class="post-it"> {{postIt.storyText}} </p>
                                                <button @click="movePostItToNext(scrumBoard, columnindex, postIt, index)" type="button" class="btn btn-info"> > </button>
                                            </div>
                                        </li>
                                    </ul>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <button @click="saveChangesProject(scrumBoard)" class="btn btn-info btn-block"> Save Changes In Project </button>
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
            const response = await fetch('api/scrumboard/save', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({userID: this.userData.userID, projectName: this.board.projectName, stories: this.board.stories.backlog})

            })
            if (!response.ok) {
                alert("Project already exists. Please choose another projectname.");
            }
            else {
                this.board = {projectName: "", userstories: {backlog: [], todo: [], inprogress: [], done: []}};
                this.getMyScrumboards();
            }
        },
        addStory() {
          this.board.stories.backlog.push(this.userStory);
          this.addedStories += this.userStory + ". ";
          this.userStory = "";
        },
        movePostItToPrevious(scrumboard, columnindex, postIt, index) {
            scrumboard.userStories[columnindex][index].boardState = this.boardColumns[(((columnindex - 1) % 4) + 4) % 4];
            scrumboard.userStories[(((columnindex - 1) % 4) + 4) % 4].push(postIt);
            scrumboard.userStories[columnindex].splice(index, 1);
        },
        movePostItToNext(scrumboard, columnindex, postIt, index) {
            scrumboard.userStories[columnindex][index].boardState = this.boardColumns[(columnindex + 1) % 4];
            scrumboard.userStories[(columnindex + 1) % 4].push(postIt);
            scrumboard.userStories[columnindex].splice(index, 1);
        },
        async getMyScrumboards() {
            const response = await fetch('api/scrumboard/myscrumboards', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({userID: this.userData.userID})
            });
            this.scrumBoards = await response.json();
        },
        async saveChangesProject(scrumBoard) {
            let userStories = []
            for (column in scrumBoard.userStories) {
                for (story in scrumBoard.userStories[column]) {
                    let userStory = []
                    userStory.push(scrumBoard.userStories[column][story].storyID);
                    userStory.push(scrumBoard.userStories[column][story].boardState);
                    userStories.push(userStory);
                }
            }
            const response = await fetch('api/scrumboard/savechanges', {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({userStories: userStories})
            })
        },
        async addMemberToProject(scrumBoard) {
            const response = await fetch('api/scrumboard/addmember', {
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
            const response = await fetch('api/scrumboard/delete', {
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


/**
 * Routes to the journal page and the scrumboard page.
 */
const routes = [{path: '/journal', component: journalScreen},
                {path: '/scrumboard', component: scrumBoard},];
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
