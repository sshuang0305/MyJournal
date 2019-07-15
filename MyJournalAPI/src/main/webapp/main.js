/**
 * The screen shown before the app starts, prompting for username.
 */
Vue.component('login-screen', {
    data() {
        return {
            username: undefined,
            password: undefined,
            errorMessage: "",
        }
    },
    template: `
        <div class=" h-100 d-flex justify-content-center align-items-center">
            <div>
                </br></br> <input placeholder="Username" v-model="username" />
                </br> <input type="password" placeholder="Password" v-model="password" />
                </br> <label class="form-check-label"><input type="checkbox"> Remember me</label>
                </br> {{ errorMessage }}
                </br> <button class="btn btn-info" v-on:click="confirmUser">Sign in</button>
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
        }
    }
});

const navigationScreen = Vue.component('navigation-screen', {
    props: ['navigationScreen'],
    template: `
        <div>
          <nav class="navbar navbar-expand-sm navbar-lighr bg-light flex-nowrap">
              <span class="navbar-text w-100">Signed in as: {{navigationScreen.username}}</span>
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
          <router-view></router-view>
        </div>
    `,
});

const journalScreen = Vue.component('journal-screen', {
  data: function() {
    return {
      date: undefined,
      notes: "",
      newNote: "",
      tasks: [],
      newTask: "",
    }
  },
  template: `
      <div>
          <h1> My journal </h1>

          <div>
            Today: {{ getDate }}
          </div>

          <div class="grid-container">

              <div class="task-list">
                  <h3> TO-DO-LIST </h3>

                  <div class="input-group mb-3">
                      <input v-model="newTask" type="text" class="form-control" placeholder="TO DO ..." >
                      <div class="input-group-append">
                          <button v-on:click="addTask" class="btn btn-info" type="button">Add</button>
                      </div>
                  </div>

                  <ul class="list-group" v-for="task in tasks">
                      <input type="checkbox">
                      <li class="list-group-item">
                          {{task}}
                          <button> X </button>
                      </li>
                  </ul>
              </div>

              <div class="notes paper">
                  <h3> Notes </h3>
                  <div> {{notes}} </div>
                  <div>
                      <textarea class="form-control" v-model="newNote" rows="1"></textarea>
                      <button v-on:click="addNote" type="button" class="btn btn-info btn-block"> Add Note </button>
                  </div>
              </div>

          </div>
      </div>
  `,
  computed: {
    getDate() {
      const today = new Date();
      const dd = String(today.getDate()).padStart(2, '0');
      const mm = String(today.getMonth() + 1).padStart(2, '0');
      const yyyy = today.getFullYear();

      this.date = dd + '/' + mm + '/' + yyyy;
      return this.date;
    }
  },
  methods: {
    addNote() {
      this.notes += this.newNote + ' ';
      this.newNote = "";
    },
    addTask() {
        this.tasks.push(this.newTask);
    }
  },
});

const scrumBoard = Vue.component('scumboard-screen', {
  data: function() {
    return {
      board: undefined,
      exampleList: [
        'item1',
        'item2',
        'item3'
      ]
    }
  },
  template: `
      <div>
          <h1> scrumboard </h1>
          <button v-on:click="createBoard" type="button" class="btn btn-success">Create New Project</button>
          <div class=scrumboard>
            {{board}}
          </div>
      </div>

  `,
  methods: {
    createBoard() {
        this.board = "hoi";
    },
  },
});

const planningPoker = Vue.component('planningpoker-screen', {
  template: `
      <div>
          <h1> planning poker </h1>
      </div>
  `,
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
        navigationScreen: undefined,
    },
    computed: {
        userConfirmed() {
            return this.navigationScreen != undefined;
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
            this.navigationScreen = await response.json();
            if (!this.navigationScreen) {
                alert("Username and password do not match");
            }
        }
    }
}).$mount('#app');
