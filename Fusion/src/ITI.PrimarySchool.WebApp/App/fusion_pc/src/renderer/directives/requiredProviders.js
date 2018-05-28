import Vue from 'vue'

export default Vue.directive('required-' +
    'provider' +
    '' +
    's', {
    bind(el, binding) {
        var providers = binding.value;

        if(!providers || !providers instanceof Array) throw new Error("v-required-providers Expected Array value.");

        el.style.visibility = 'visible';
    }
});

 