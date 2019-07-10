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
        <div>
            <input placeholder="Username" v-model="username" />
            <input placeholder="Password" v-model="password" />

            {{ errorMessage }}
            <button v-on:click="confirmUser">Confirm</button>
        </div>
    `,
    methods: {
        confirmUser() {
            if (!this.user) {
                this.errorMessage = "Username is required";
                return;
            }
            if (!this.password) {
              this.errorMessage = "Password is required";
              return;
            }
            this.errorMessage = "";
            this.$emit('user-confirmed', this.user, this.passsword);
        }
    }
});

Vue.component('home-screen', {
    props: ['homeScreen'],
    template: `
        <div>
            <h1 class="title"> HOME SCREEN </h1>
            {{homeScreen.username}}
            {{homeScreen.password}}
        </div>
    `,
});

const app = new Vue({
    el: '#app',

    data: {
        homeScreen: undefined,
    },

    template: `
        <div>
            <div class="jumbotron jumbotron-fluid">
                <div class="container">
                    <h1> MY JOURNAL </h1>
                    <p> DEVELOP, REFLECT & REPEAT </p>
                </div>
            </div>
        </div>
    `,
    computed: {
        userConfirmed() {
            return homeScreen != undefined;
        },
    },
    methods: {
        showScreen(username, password) {
            this.homeScreen = {"homeScreen": [{"user": user},{"password": user}];
        }
    }
});
