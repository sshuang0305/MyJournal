/**
 * The screen shown before the app starts, prompting for username.
 */
Vue.component('start-screen', {
    data() {
        return {
            user: undefined,
            errorMessage: "",
        }
    },
    template: `
        <div>
            <input placeholder="Username" v-model="playerOne" />
            {{ errorMessage }}
            <button v-on:click="confirmUser">Confirm</button>
        </div>
    `,
    methods: {
        confirmUser() {
            if (!this.user) {
                this.errorMessage = "Username is required";
            }
            this.errorMessage = "";
            this.$emit('user-confirmed', this.user);
        }
    }
});

Vue.component('home-screen', {
    props: ['homeScreen'],
    template: `
        <div>
            <h1 class="title"> HOME SCREEN </h1>
            {{homeScreen.user}}
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
        showScreen(user) {
            this.homeScreen = {"user": user};
        }
    }
});
