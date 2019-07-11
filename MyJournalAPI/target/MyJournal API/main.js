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
                </br> <button class="btn btn-primary" v-on:click="confirmUser">Sign in</button>
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
              <span class="navbar-text w-100">Signed in as: {{navigationScreen.user}}</span>
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
  template: `
      <div>
          <h1> journal </h1>
      </div>
  `,
});

const scrumBoard = Vue.component('scumboard-screen', {
  template: `
      <div>
          <h1> scrumboard </h1>
      </div>
  `,
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
            const response = await fetch('api/users', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username: username , password: password })
            });
            this.navigationScreen = {"user": username, "password": password};
        }
    }
}).$mount('#app');
