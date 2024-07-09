import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/dialog/theme/lumo/vaadin-dialog.js';
import 'Frontend/generated/jar-resources/flow-component-renderer.js';
import '@vaadin/login/theme/lumo/vaadin-login-form.js';
import '@vaadin/vertical-layout/theme/lumo/vaadin-vertical-layout.js';
import '@vaadin/notification/theme/lumo/vaadin-notification.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '8e7862a96b5120d3c2458bca435851132de04cd22be62b0a683fbc875c25a317') {
    pending.push(import('./chunks/chunk-8e7862a96b5120d3c2458bca435851132de04cd22be62b0a683fbc875c25a317.js'));
  }
  if (key === '15f3d602f00ac1b012b5ee9fccc538ece70fcb3aac793e2dccac25905db4a94d') {
    pending.push(import('./chunks/chunk-15f3d602f00ac1b012b5ee9fccc538ece70fcb3aac793e2dccac25905db4a94d.js'));
  }
  if (key === '099d209d672475ec46e9767d790f597b2de23f467dd14befec6b8004fbdcbc19') {
    pending.push(import('./chunks/chunk-099d209d672475ec46e9767d790f597b2de23f467dd14befec6b8004fbdcbc19.js'));
  }
  if (key === '72201d1ba6075d9af55be9892b7115ced39f2c5825dfcbb3148c7045e1f01495') {
    pending.push(import('./chunks/chunk-72201d1ba6075d9af55be9892b7115ced39f2c5825dfcbb3148c7045e1f01495.js'));
  }
  if (key === 'b78b8f58ad51bb05fa447f2bbcfd524d1bf1966730273035d44d3633eb48dad4') {
    pending.push(import('./chunks/chunk-b78b8f58ad51bb05fa447f2bbcfd524d1bf1966730273035d44d3633eb48dad4.js'));
  }
  if (key === 'c070369c57c19cd6965406b0f21e92d049a257cdbd546a4f66e4e617e0a4bb58') {
    pending.push(import('./chunks/chunk-c070369c57c19cd6965406b0f21e92d049a257cdbd546a4f66e4e617e0a4bb58.js'));
  }
  if (key === '7c9e4f0c06a2074df22be9ea949df1b9c0a29f9a45839a5e2ab69c5cb9619a14') {
    pending.push(import('./chunks/chunk-7c9e4f0c06a2074df22be9ea949df1b9c0a29f9a45839a5e2ab69c5cb9619a14.js'));
  }
  if (key === '4a3608fff5ec2424e3c504f8045ed2a5de3cdace339b5047ae1a55e7cf937b74') {
    pending.push(import('./chunks/chunk-4a3608fff5ec2424e3c504f8045ed2a5de3cdace339b5047ae1a55e7cf937b74.js'));
  }
  if (key === '7dcbdb559e124b8b5623f32d263bbcf90de1c70b5c0e95d1551a81d1aa3dbca0') {
    pending.push(import('./chunks/chunk-7dcbdb559e124b8b5623f32d263bbcf90de1c70b5c0e95d1551a81d1aa3dbca0.js'));
  }
  if (key === '7355fb9aa8b60c7c02a74bbe69f5d3e3f66b0f909d1cb18ed42f7d05a260e03f') {
    pending.push(import('./chunks/chunk-7355fb9aa8b60c7c02a74bbe69f5d3e3f66b0f909d1cb18ed42f7d05a260e03f.js'));
  }
  if (key === 'bf389a12747219ecc94cc6e7c89803537333cb4efd5f4fc0b09732377b7f7367') {
    pending.push(import('./chunks/chunk-bf389a12747219ecc94cc6e7c89803537333cb4efd5f4fc0b09732377b7f7367.js'));
  }
  if (key === '6edaae777cad4f6a9e2c45bd93d82d7e09c96f014ddfc2377b10fea990ba3e7c') {
    pending.push(import('./chunks/chunk-6edaae777cad4f6a9e2c45bd93d82d7e09c96f014ddfc2377b10fea990ba3e7c.js'));
  }
  if (key === 'fbe1103e7988c1bc1763c5439159387ab4249c9796de0dab25b48f7344ed0829') {
    pending.push(import('./chunks/chunk-fbe1103e7988c1bc1763c5439159387ab4249c9796de0dab25b48f7344ed0829.js'));
  }
  if (key === '1fe8e699e1a68349290e2e06206dd068ba616c488494e94383d30a3e287ec3f9') {
    pending.push(import('./chunks/chunk-1fe8e699e1a68349290e2e06206dd068ba616c488494e94383d30a3e287ec3f9.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;