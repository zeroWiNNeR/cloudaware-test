import Vue from 'vue'

import vuetify from './plugins/vuetify'
import resource from './plugins/resource'
import App from './App.vue'

Vue.config.productionTip = true

new Vue({
    el: '#app',
    vuetify,
    resource,
    render: h => h(App),
}).$mount('#app')
