/**
 * The screen shown before the app starts, prompting for username.
 */
Vue.component('start-screen', {
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

Vue.component('home-screen', {
    props: ['homeScreen'],
    template: `
        <div>
          <nav class="navbar navbar-expand-sm bg-light navbar-light justify-content-center">
              <ul class="navbar-nav">
                  <li class="nav-item active">
                      <a class="nav-link" href="#">Home</a>
                  </li>
                  <li class="nav-item">
                      <a class="nav-link" href="#">Scrumboard</a>
                  </li>
                  <li class="nav-item">
                      <a class="nav-link" href="#" >Planning Poker</a>
                  </li>
              </ul>
          </nav>

          <h1 class="title"> HOME SCREEN </h1>
          <p> {{homeScreen.user}} </p>
          <p> {{homeScreen.password}} </p>

        </div>
    `,
});

const app = new Vue({
    el: '#app',
    data: {
        homeScreen: undefined,
    },


    computed: {
        userConfirmed() {
            return this.homeScreen != undefined;
        },
    },
    methods: {
        showScreen(username, password) {
            this.homeScreen = {"user": username,"password": password};
        }
    }
});
