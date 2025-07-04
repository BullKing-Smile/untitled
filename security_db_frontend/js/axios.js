/*! Axios v1.8.4 Copyright (c) 2025 Matt Zabriskie and contributors */
!function (e, t) {
    "object" == typeof exports && "undefined" != typeof module ? module.exports = t() : "function" == typeof define && define.amd ? define(t) : (e = "undefined" != typeof globalThis ? globalThis : e || self).axios = t()
}(this, (function () {
    "use strict";

    function e(e) {
        var r, n;

        function o(r, n) {
            try {
                var a = e[r](n), s = a.value, u = s instanceof t;
                Promise.resolve(u ? s.v : s).then((function (t) {
                    if (u) {
                        var n = "return" === r ? "return" : "next";
                        if (!s.k || t.done) return o(n, t);
                        t = e[n](t).value
                    }
                    i(a.done ? "return" : "normal", t)
                }), (function (e) {
                    o("throw", e)
                }))
            } catch (e) {
                i("throw", e)
            }
        }

        function i(e, t) {
            switch (e) {
                case"return":
                    r.resolve({value: t, done: !0});
                    break;
                case"throw":
                    r.reject(t);
                    break;
                default:
                    r.resolve({value: t, done: !1})
            }
            (r = r.next) ? o(r.key, r.arg) : n = null
        }

        this._invoke = function (e, t) {
            return new Promise((function (i, a) {
                var s = {key: e, arg: t, resolve: i, reject: a, next: null};
                n ? n = n.next = s : (r = n = s, o(e, t))
            }))
        }, "function" != typeof e.return && (this.return = void 0)
    }

    function t(e, t) {
        this.v = e, this.k = t
    }

    function r(e) {
        var r = {}, n = !1;

        function o(r, o) {
            return n = !0, o = new Promise((function (t) {
                t(e[r](o))
            })), {done: !1, value: new t(o, 1)}
        }

        return r["undefined" != typeof Symbol && Symbol.iterator || "@@iterator"] = function () {
            return this
        }, r.next = function (e) {
            return n ? (n = !1, e) : o("next", e)
        }, "function" == typeof e.throw && (r.throw = function (e) {
            if (n) throw n = !1, e;
            return o("throw", e)
        }), "function" == typeof e.return && (r.return = function (e) {
            return n ? (n = !1, e) : o("return", e)
        }), r
    }

    function n(e) {
        var t, r, n, i = 2;
        for ("undefined" != typeof Symbol && (r = Symbol.asyncIterator, n = Symbol.iterator); i--;) {
            if (r && null != (t = e[r])) return t.call(e);
            if (n && null != (t = e[n])) return new o(t.call(e));
            r = "@@asyncIterator", n = "@@iterator"
        }
        throw new TypeError("Object is not async iterable")
    }

    function o(e) {
        function t(e) {
            if (Object(e) !== e) return Promise.reject(new TypeError(e + " is not an object."));
            var t = e.done;
            return Promise.resolve(e.value).then((function (e) {
                return {value: e, done: t}
            }))
        }

        return o = function (e) {
            this.s = e, this.n = e.next
        }, o.prototype = {
            s: null, n: null, next: function () {
                return t(this.n.apply(this.s, arguments))
            }, return: function (e) {
                var r = this.s.return;
                return void 0 === r ? Promise.resolve({value: e, done: !0}) : t(r.apply(this.s, arguments))
            }, throw: function (e) {
                var r = this.s.return;
                return void 0 === r ? Promise.reject(e) : t(r.apply(this.s, arguments))
            }
        }, new o(e)
    }

    function i(e) {
        return new t(e, 0)
    }

    function a(e, t) {
        var r = Object.keys(e);
        if (Object.getOwnPropertySymbols) {
            var n = Object.getOwnPropertySymbols(e);
            t && (n = n.filter((function (t) {
                return Object.getOwnPropertyDescriptor(e, t).enumerable
            }))), r.push.apply(r, n)
        }
        return r
    }

    function s(e) {
        for (var t = 1; t < arguments.length; t++) {
            var r = null != arguments[t] ? arguments[t] : {};
            t % 2 ? a(Object(r), !0).forEach((function (t) {
                m(e, t, r[t])
            })) : Object.getOwnPropertyDescriptors ? Object.defineProperties(e, Object.getOwnPropertyDescriptors(r)) : a(Object(r)).forEach((function (t) {
                Object.defineProperty(e, t, Object.getOwnPropertyDescriptor(r, t))
            }))
        }
        return e
    }

    function u() {
        u = function () {
            return t
        };
        var e, t = {}, r = Object.prototype, n = r.hasOwnProperty, o = Object.defineProperty || function (e, t, r) {
                e[t] = r.value
            }, i = "function" == typeof Symbol ? Symbol : {}, a = i.iterator || "@@iterator",
            s = i.asyncIterator || "@@asyncIterator", c = i.toStringTag || "@@toStringTag";

        function f(e, t, r) {
            return Object.defineProperty(e, t, {value: r, enumerable: !0, configurable: !0, writable: !0}), e[t]
        }

        try {
            f({}, "")
        } catch (e) {
            f = function (e, t, r) {
                return e[t] = r
            }
        }

        function l(e, t, r, n) {
            var i = t && t.prototype instanceof m ? t : m, a = Object.create(i.prototype), s = new P(n || []);
            return o(a, "_invoke", {value: k(e, r, s)}), a
        }

        function p(e, t, r) {
            try {
                return {type: "normal", arg: e.call(t, r)}
            } catch (e) {
                return {type: "throw", arg: e}
            }
        }

        t.wrap = l;
        var h = "suspendedStart", d = "executing", v = "completed", y = {};

        function m() {
        }

        function b() {
        }

        function g() {
        }

        var w = {};
        f(w, a, (function () {
            return this
        }));
        var E = Object.getPrototypeOf, O = E && E(E(L([])));
        O && O !== r && n.call(O, a) && (w = O);
        var S = g.prototype = m.prototype = Object.create(w);

        function x(e) {
            ["next", "throw", "return"].forEach((function (t) {
                f(e, t, (function (e) {
                    return this._invoke(t, e)
                }))
            }))
        }

        function R(e, t) {
            function r(o, i, a, s) {
                var u = p(e[o], e, i);
                if ("throw" !== u.type) {
                    var c = u.arg, f = c.value;
                    return f && "object" == typeof f && n.call(f, "__await") ? t.resolve(f.__await).then((function (e) {
                        r("next", e, a, s)
                    }), (function (e) {
                        r("throw", e, a, s)
                    })) : t.resolve(f).then((function (e) {
                        c.value = e, a(c)
                    }), (function (e) {
                        return r("throw", e, a, s)
                    }))
                }
                s(u.arg)
            }

            var i;
            o(this, "_invoke", {
                value: function (e, n) {
                    function o() {
                        return new t((function (t, o) {
                            r(e, n, t, o)
                        }))
                    }

                    return i = i ? i.then(o, o) : o()
                }
            })
        }

        function k(t, r, n) {
            var o = h;
            return function (i, a) {
                if (o === d) throw new Error("Generator is already running");
                if (o === v) {
                    if ("throw" === i) throw a;
                    return {value: e, done: !0}
                }
                for (n.method = i, n.arg = a; ;) {
                    var s = n.delegate;
                    if (s) {
                        var u = T(s, n);
                        if (u) {
                            if (u === y) continue;
                            return u
                        }
                    }
                    if ("next" === n.method) n.sent = n._sent = n.arg; else if ("throw" === n.method) {
                        if (o === h) throw o = v, n.arg;
                        n.dispatchException(n.arg)
                    } else "return" === n.method && n.abrupt("return", n.arg);
                    o = d;
                    var c = p(t, r, n);
                    if ("normal" === c.type) {
                        if (o = n.done ? v : "suspendedYield", c.arg === y) continue;
                        return {value: c.arg, done: n.done}
                    }
                    "throw" === c.type && (o = v, n.method = "throw", n.arg = c.arg)
                }
            }
        }

        function T(t, r) {
            var n = r.method, o = t.iterator[n];
            if (o === e) return r.delegate = null, "throw" === n && t.iterator.return && (r.method = "return", r.arg = e, T(t, r), "throw" === r.method) || "return" !== n && (r.method = "throw", r.arg = new TypeError("The iterator does not provide a '" + n + "' method")), y;
            var i = p(o, t.iterator, r.arg);
            if ("throw" === i.type) return r.method = "throw", r.arg = i.arg, r.delegate = null, y;
            var a = i.arg;
            return a ? a.done ? (r[t.resultName] = a.value, r.next = t.nextLoc, "return" !== r.method && (r.method = "next", r.arg = e), r.delegate = null, y) : a : (r.method = "throw", r.arg = new TypeError("iterator result is not an object"), r.delegate = null, y)
        }

        function j(e) {
            var t = {tryLoc: e[0]};
            1 in e && (t.catchLoc = e[1]), 2 in e && (t.finallyLoc = e[2], t.afterLoc = e[3]), this.tryEntries.push(t)
        }

        function A(e) {
            var t = e.completion || {};
            t.type = "normal", delete t.arg, e.completion = t
        }

        function P(e) {
            this.tryEntries = [{tryLoc: "root"}], e.forEach(j, this), this.reset(!0)
        }

        function L(t) {
            if (t || "" === t) {
                var r = t[a];
                if (r) return r.call(t);
                if ("function" == typeof t.next) return t;
                if (!isNaN(t.length)) {
                    var o = -1, i = function r() {
                        for (; ++o < t.length;) if (n.call(t, o)) return r.value = t[o], r.done = !1, r;
                        return r.value = e, r.done = !0, r
                    };
                    return i.next = i
                }
            }
            throw new TypeError(typeof t + " is not iterable")
        }

        return b.prototype = g, o(S, "constructor", {value: g, configurable: !0}), o(g, "constructor", {
            value: b,
            configurable: !0
        }), b.displayName = f(g, c, "GeneratorFunction"), t.isGeneratorFunction = function (e) {
            var t = "function" == typeof e && e.constructor;
            return !!t && (t === b || "GeneratorFunction" === (t.displayName || t.name))
        }, t.mark = function (e) {
            return Object.setPrototypeOf ? Object.setPrototypeOf(e, g) : (e.__proto__ = g, f(e, c, "GeneratorFunction")), e.prototype = Object.create(S), e
        }, t.awrap = function (e) {
            return {__await: e}
        }, x(R.prototype), f(R.prototype, s, (function () {
            return this
        })), t.AsyncIterator = R, t.async = function (e, r, n, o, i) {
            void 0 === i && (i = Promise);
            var a = new R(l(e, r, n, o), i);
            return t.isGeneratorFunction(r) ? a : a.next().then((function (e) {
                return e.done ? e.value : a.next()
            }))
        }, x(S), f(S, c, "Generator"), f(S, a, (function () {
            return this
        })), f(S, "toString", (function () {
            return "[object Generator]"
        })), t.keys = function (e) {
            var t = Object(e), r = [];
            for (var n in t) r.push(n);
            return r.reverse(), function e() {
                for (; r.length;) {
                    var n = r.pop();
                    if (n in t) return e.value = n, e.done = !1, e
                }
                return e.done = !0, e
            }
        }, t.values = L, P.prototype = {
            constructor: P, reset: function (t) {
                if (this.prev = 0, this.next = 0, this.sent = this._sent = e, this.done = !1, this.delegate = null, this.method = "next", this.arg = e, this.tryEntries.forEach(A), !t) for (var r in this) "t" === r.charAt(0) && n.call(this, r) && !isNaN(+r.slice(1)) && (this[r] = e)
            }, stop: function () {
                this.done = !0;
                var e = this.tryEntries[0].completion;
                if ("throw" === e.type) throw e.arg;
                return this.rval
            }, dispatchException: function (t) {
                if (this.done) throw t;
                var r = this;

                function o(n, o) {
                    return s.type = "throw", s.arg = t, r.next = n, o && (r.method = "next", r.arg = e), !!o
                }

                for (var i = this.tryEntries.length - 1; i >= 0; --i) {
                    var a = this.tryEntries[i], s = a.completion;
                    if ("root" === a.tryLoc) return o("end");
                    if (a.tryLoc <= this.prev) {
                        var u = n.call(a, "catchLoc"), c = n.call(a, "finallyLoc");
                        if (u && c) {
                            if (this.prev < a.catchLoc) return o(a.catchLoc, !0);
                            if (this.prev < a.finallyLoc) return o(a.finallyLoc)
                        } else if (u) {
                            if (this.prev < a.catchLoc) return o(a.catchLoc, !0)
                        } else {
                            if (!c) throw new Error("try statement without catch or finally");
                            if (this.prev < a.finallyLoc) return o(a.finallyLoc)
                        }
                    }
                }
            }, abrupt: function (e, t) {
                for (var r = this.tryEntries.length - 1; r >= 0; --r) {
                    var o = this.tryEntries[r];
                    if (o.tryLoc <= this.prev && n.call(o, "finallyLoc") && this.prev < o.finallyLoc) {
                        var i = o;
                        break
                    }
                }
                i && ("break" === e || "continue" === e) && i.tryLoc <= t && t <= i.finallyLoc && (i = null);
                var a = i ? i.completion : {};
                return a.type = e, a.arg = t, i ? (this.method = "next", this.next = i.finallyLoc, y) : this.complete(a)
            }, complete: function (e, t) {
                if ("throw" === e.type) throw e.arg;
                return "break" === e.type || "continue" === e.type ? this.next = e.arg : "return" === e.type ? (this.rval = this.arg = e.arg, this.method = "return", this.next = "end") : "normal" === e.type && t && (this.next = t), y
            }, finish: function (e) {
                for (var t = this.tryEntries.length - 1; t >= 0; --t) {
                    var r = this.tryEntries[t];
                    if (r.finallyLoc === e) return this.complete(r.completion, r.afterLoc), A(r), y
                }
            }, catch: function (e) {
                for (var t = this.tryEntries.length - 1; t >= 0; --t) {
                    var r = this.tryEntries[t];
                    if (r.tryLoc === e) {
                        var n = r.completion;
                        if ("throw" === n.type) {
                            var o = n.arg;
                            A(r)
                        }
                        return o
                    }
                }
                throw new Error("illegal catch attempt")
            }, delegateYield: function (t, r, n) {
                return this.delegate = {
                    iterator: L(t),
                    resultName: r,
                    nextLoc: n
                }, "next" === this.method && (this.arg = e), y
            }
        }, t
    }

    function c(e) {
        var t = function (e, t) {
            if ("object" != typeof e || !e) return e;
            var r = e[Symbol.toPrimitive];
            if (void 0 !== r) {
                var n = r.call(e, t || "default");
                if ("object" != typeof n) return n;
                throw new TypeError("@@toPrimitive must return a primitive value.")
            }
            return ("string" === t ? String : Number)(e)
        }(e, "string");
        return "symbol" == typeof t ? t : String(t)
    }

    function f(e) {
        return f = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (e) {
            return typeof e
        } : function (e) {
            return e && "function" == typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : typeof e
        }, f(e)
    }

    function l(t) {
        return function () {
            return new e(t.apply(this, arguments))
        }
    }

    function p(e, t, r, n, o, i, a) {
        try {
            var s = e[i](a), u = s.value
        } catch (e) {
            return void r(e)
        }
        s.done ? t(u) : Promise.resolve(u).then(n, o)
    }

    function h(e) {
        return function () {
            var t = this, r = arguments;
            return new Promise((function (n, o) {
                var i = e.apply(t, r);

                function a(e) {
                    p(i, n, o, a, s, "next", e)
                }

                function s(e) {
                    p(i, n, o, a, s, "throw", e)
                }

                a(void 0)
            }))
        }
    }

    function d(e, t) {
        if (!(e instanceof t)) throw new TypeError("Cannot call a class as a function")
    }

    function v(e, t) {
        for (var r = 0; r < t.length; r++) {
            var n = t[r];
            n.enumerable = n.enumerable || !1, n.configurable = !0, "value" in n && (n.writable = !0), Object.defineProperty(e, c(n.key), n)
        }
    }

    function y(e, t, r) {
        return t && v(e.prototype, t), r && v(e, r), Object.defineProperty(e, "prototype", {writable: !1}), e
    }

    function m(e, t, r) {
        return (t = c(t)) in e ? Object.defineProperty(e, t, {
            value: r,
            enumerable: !0,
            configurable: !0,
            writable: !0
        }) : e[t] = r, e
    }

    function b(e, t) {
        return w(e) || function (e, t) {
            var r = null == e ? null : "undefined" != typeof Symbol && e[Symbol.iterator] || e["@@iterator"];
            if (null != r) {
                var n, o, i, a, s = [], u = !0, c = !1;
                try {
                    if (i = (r = r.call(e)).next, 0 === t) {
                        if (Object(r) !== r) return;
                        u = !1
                    } else for (; !(u = (n = i.call(r)).done) && (s.push(n.value), s.length !== t); u = !0) ;
                } catch (e) {
                    c = !0, o = e
                } finally {
                    try {
                        if (!u && null != r.return && (a = r.return(), Object(a) !== a)) return
                    } finally {
                        if (c) throw o
                    }
                }
                return s
            }
        }(e, t) || O(e, t) || x()
    }

    function g(e) {
        return function (e) {
            if (Array.isArray(e)) return S(e)
        }(e) || E(e) || O(e) || function () {
            throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")
        }()
    }

    function w(e) {
        if (Array.isArray(e)) return e
    }

    function E(e) {
        if ("undefined" != typeof Symbol && null != e[Symbol.iterator] || null != e["@@iterator"]) return Array.from(e)
    }

    function O(e, t) {
        if (e) {
            if ("string" == typeof e) return S(e, t);
            var r = Object.prototype.toString.call(e).slice(8, -1);
            return "Object" === r && e.constructor && (r = e.constructor.name), "Map" === r || "Set" === r ? Array.from(e) : "Arguments" === r || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r) ? S(e, t) : void 0
        }
    }

    function S(e, t) {
        (null == t || t > e.length) && (t = e.length);
        for (var r = 0, n = new Array(t); r < t; r++) n[r] = e[r];
        return n
    }

    function x() {
        throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")
    }

    function R(e, t) {
        return function () {
            return e.apply(t, arguments)
        }
    }

    e.prototype["function" == typeof Symbol && Symbol.asyncIterator || "@@asyncIterator"] = function () {
        return this
    }, e.prototype.next = function (e) {
        return this._invoke("next", e)
    }, e.prototype.throw = function (e) {
        return this._invoke("throw", e)
    }, e.prototype.return = function (e) {
        return this._invoke("return", e)
    };
    var k, T = Object.prototype.toString, j = Object.getPrototypeOf, A = (k = Object.create(null), function (e) {
        var t = T.call(e);
        return k[t] || (k[t] = t.slice(8, -1).toLowerCase())
    }), P = function (e) {
        return e = e.toLowerCase(), function (t) {
            return A(t) === e
        }
    }, L = function (e) {
        return function (t) {
            return f(t) === e
        }
    }, N = Array.isArray, _ = L("undefined");
    var C = P("ArrayBuffer");
    var U = L("string"), F = L("function"), B = L("number"), D = function (e) {
            return null !== e && "object" === f(e)
        }, q = function (e) {
            if ("object" !== A(e)) return !1;
            var t = j(e);
            return !(null !== t && t !== Object.prototype && null !== Object.getPrototypeOf(t) || Symbol.toStringTag in e || Symbol.iterator in e)
        }, I = P("Date"), M = P("File"), z = P("Blob"), H = P("FileList"), J = P("URLSearchParams"),
        W = b(["ReadableStream", "Request", "Response", "Headers"].map(P), 4), K = W[0], V = W[1], G = W[2], X = W[3];

    function $(e, t) {
        var r, n, o = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : {}, i = o.allOwnKeys,
            a = void 0 !== i && i;
        if (null != e) if ("object" !== f(e) && (e = [e]), N(e)) for (r = 0, n = e.length; r < n; r++) t.call(null, e[r], r, e); else {
            var s, u = a ? Object.getOwnPropertyNames(e) : Object.keys(e), c = u.length;
            for (r = 0; r < c; r++) s = u[r], t.call(null, e[s], s, e)
        }
    }

    function Y(e, t) {
        t = t.toLowerCase();
        for (var r, n = Object.keys(e), o = n.length; o-- > 0;) if (t === (r = n[o]).toLowerCase()) return r;
        return null
    }

    var Q = "undefined" != typeof globalThis ? globalThis : "undefined" != typeof self ? self : "undefined" != typeof window ? window : global,
        Z = function (e) {
            return !_(e) && e !== Q
        };
    var ee, te = (ee = "undefined" != typeof Uint8Array && j(Uint8Array), function (e) {
        return ee && e instanceof ee
    }), re = P("HTMLFormElement"), ne = function (e) {
        var t = Object.prototype.hasOwnProperty;
        return function (e, r) {
            return t.call(e, r)
        }
    }(), oe = P("RegExp"), ie = function (e, t) {
        var r = Object.getOwnPropertyDescriptors(e), n = {};
        $(r, (function (r, o) {
            var i;
            !1 !== (i = t(r, o, e)) && (n[o] = i || r)
        })), Object.defineProperties(e, n)
    };
    var ae, se, ue, ce, fe = P("AsyncFunction"),
        le = (ae = "function" == typeof setImmediate, se = F(Q.postMessage), ae ? setImmediate : se ? (ue = "axios@".concat(Math.random()), ce = [], Q.addEventListener("message", (function (e) {
            var t = e.source, r = e.data;
            t === Q && r === ue && ce.length && ce.shift()()
        }), !1), function (e) {
            ce.push(e), Q.postMessage(ue, "*")
        }) : function (e) {
            return setTimeout(e)
        }),
        pe = "undefined" != typeof queueMicrotask ? queueMicrotask.bind(Q) : "undefined" != typeof process && process.nextTick || le,
        he = {
            isArray: N,
            isArrayBuffer: C,
            isBuffer: function (e) {
                return null !== e && !_(e) && null !== e.constructor && !_(e.constructor) && F(e.constructor.isBuffer) && e.constructor.isBuffer(e)
            },
            isFormData: function (e) {
                var t;
                return e && ("function" == typeof FormData && e instanceof FormData || F(e.append) && ("formdata" === (t = A(e)) || "object" === t && F(e.toString) && "[object FormData]" === e.toString()))
            },
            isArrayBufferView: function (e) {
                return "undefined" != typeof ArrayBuffer && ArrayBuffer.isView ? ArrayBuffer.isView(e) : e && e.buffer && C(e.buffer)
            },
            isString: U,
            isNumber: B,
            isBoolean: function (e) {
                return !0 === e || !1 === e
            },
            isObject: D,
            isPlainObject: q,
            isReadableStream: K,
            isRequest: V,
            isResponse: G,
            isHeaders: X,
            isUndefined: _,
            isDate: I,
            isFile: M,
            isBlob: z,
            isRegExp: oe,
            isFunction: F,
            isStream: function (e) {
                return D(e) && F(e.pipe)
            },
            isURLSearchParams: J,
            isTypedArray: te,
            isFileList: H,
            forEach: $,
            merge: function e() {
                for (var t = Z(this) && this || {}, r = t.caseless, n = {}, o = function (t, o) {
                    var i = r && Y(n, o) || o;
                    q(n[i]) && q(t) ? n[i] = e(n[i], t) : q(t) ? n[i] = e({}, t) : N(t) ? n[i] = t.slice() : n[i] = t
                }, i = 0, a = arguments.length; i < a; i++) arguments[i] && $(arguments[i], o);
                return n
            },
            extend: function (e, t, r) {
                var n = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : {}, o = n.allOwnKeys;
                return $(t, (function (t, n) {
                    r && F(t) ? e[n] = R(t, r) : e[n] = t
                }), {allOwnKeys: o}), e
            },
            trim: function (e) {
                return e.trim ? e.trim() : e.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, "")
            },
            stripBOM: function (e) {
                return 65279 === e.charCodeAt(0) && (e = e.slice(1)), e
            },
            inherits: function (e, t, r, n) {
                e.prototype = Object.create(t.prototype, n), e.prototype.constructor = e, Object.defineProperty(e, "super", {value: t.prototype}), r && Object.assign(e.prototype, r)
            },
            toFlatObject: function (e, t, r, n) {
                var o, i, a, s = {};
                if (t = t || {}, null == e) return t;
                do {
                    for (i = (o = Object.getOwnPropertyNames(e)).length; i-- > 0;) a = o[i], n && !n(a, e, t) || s[a] || (t[a] = e[a], s[a] = !0);
                    e = !1 !== r && j(e)
                } while (e && (!r || r(e, t)) && e !== Object.prototype);
                return t
            },
            kindOf: A,
            kindOfTest: P,
            endsWith: function (e, t, r) {
                e = String(e), (void 0 === r || r > e.length) && (r = e.length), r -= t.length;
                var n = e.indexOf(t, r);
                return -1 !== n && n === r
            },
            toArray: function (e) {
                if (!e) return null;
                if (N(e)) return e;
                var t = e.length;
                if (!B(t)) return null;
                for (var r = new Array(t); t-- > 0;) r[t] = e[t];
                return r
            },
            forEachEntry: function (e, t) {
                for (var r, n = (e && e[Symbol.iterator]).call(e); (r = n.next()) && !r.done;) {
                    var o = r.value;
                    t.call(e, o[0], o[1])
                }
            },
            matchAll: function (e, t) {
                for (var r, n = []; null !== (r = e.exec(t));) n.push(r);
                return n
            },
            isHTMLForm: re,
            hasOwnProperty: ne,
            hasOwnProp: ne,
            reduceDescriptors: ie,
            freezeMethods: function (e) {
                ie(e, (function (t, r) {
                    if (F(e) && -1 !== ["arguments", "caller", "callee"].indexOf(r)) return !1;
                    var n = e[r];
                    F(n) && (t.enumerable = !1, "writable" in t ? t.writable = !1 : t.set || (t.set = function () {
                        throw Error("Can not rewrite read-only method '" + r + "'")
                    }))
                }))
            },
            toObjectSet: function (e, t) {
                var r = {}, n = function (e) {
                    e.forEach((function (e) {
                        r[e] = !0
                    }))
                };
                return N(e) ? n(e) : n(String(e).split(t)), r
            },
            toCamelCase: function (e) {
                return e.toLowerCase().replace(/[-_\s]([a-z\d])(\w*)/g, (function (e, t, r) {
                    return t.toUpperCase() + r
                }))
            },
            noop: function () {
            },
            toFiniteNumber: function (e, t) {
                return null != e && Number.isFinite(e = +e) ? e : t
            },
            findKey: Y,
            global: Q,
            isContextDefined: Z,
            isSpecCompliantForm: function (e) {
                return !!(e && F(e.append) && "FormData" === e[Symbol.toStringTag] && e[Symbol.iterator])
            },
            toJSONObject: function (e) {
                var t = new Array(10);
                return function e(r, n) {
                    if (D(r)) {
                        if (t.indexOf(r) >= 0) return;
                        if (!("toJSON" in r)) {
                            t[n] = r;
                            var o = N(r) ? [] : {};
                            return $(r, (function (t, r) {
                                var i = e(t, n + 1);
                                !_(i) && (o[r] = i)
                            })), t[n] = void 0, o
                        }
                    }
                    return r
                }(e, 0)
            },
            isAsyncFn: fe,
            isThenable: function (e) {
                return e && (D(e) || F(e)) && F(e.then) && F(e.catch)
            },
            setImmediate: le,
            asap: pe
        };

    function de(e, t, r, n, o) {
        Error.call(this), Error.captureStackTrace ? Error.captureStackTrace(this, this.constructor) : this.stack = (new Error).stack, this.message = e, this.name = "AxiosError", t && (this.code = t), r && (this.config = r), n && (this.request = n), o && (this.response = o, this.status = o.status ? o.status : null)
    }

    he.inherits(de, Error, {
        toJSON: function () {
            return {
                message: this.message,
                name: this.name,
                description: this.description,
                number: this.number,
                fileName: this.fileName,
                lineNumber: this.lineNumber,
                columnNumber: this.columnNumber,
                stack: this.stack,
                config: he.toJSONObject(this.config),
                code: this.code,
                status: this.status
            }
        }
    });
    var ve = de.prototype, ye = {};
    ["ERR_BAD_OPTION_VALUE", "ERR_BAD_OPTION", "ECONNABORTED", "ETIMEDOUT", "ERR_NETWORK", "ERR_FR_TOO_MANY_REDIRECTS", "ERR_DEPRECATED", "ERR_BAD_RESPONSE", "ERR_BAD_REQUEST", "ERR_CANCELED", "ERR_NOT_SUPPORT", "ERR_INVALID_URL"].forEach((function (e) {
        ye[e] = {value: e}
    })), Object.defineProperties(de, ye), Object.defineProperty(ve, "isAxiosError", {value: !0}), de.from = function (e, t, r, n, o, i) {
        var a = Object.create(ve);
        return he.toFlatObject(e, a, (function (e) {
            return e !== Error.prototype
        }), (function (e) {
            return "isAxiosError" !== e
        })), de.call(a, e.message, t, r, n, o), a.cause = e, a.name = e.name, i && Object.assign(a, i), a
    };

    function me(e) {
        return he.isPlainObject(e) || he.isArray(e)
    }

    function be(e) {
        return he.endsWith(e, "[]") ? e.slice(0, -2) : e
    }

    function ge(e, t, r) {
        return e ? e.concat(t).map((function (e, t) {
            return e = be(e), !r && t ? "[" + e + "]" : e
        })).join(r ? "." : "") : t
    }

    var we = he.toFlatObject(he, {}, null, (function (e) {
        return /^is[A-Z]/.test(e)
    }));

    function Ee(e, t, r) {
        if (!he.isObject(e)) throw new TypeError("target must be an object");
        t = t || new FormData;
        var n = (r = he.toFlatObject(r, {metaTokens: !0, dots: !1, indexes: !1}, !1, (function (e, t) {
                return !he.isUndefined(t[e])
            }))).metaTokens, o = r.visitor || c, i = r.dots, a = r.indexes,
            s = (r.Blob || "undefined" != typeof Blob && Blob) && he.isSpecCompliantForm(t);
        if (!he.isFunction(o)) throw new TypeError("visitor must be a function");

        function u(e) {
            if (null === e) return "";
            if (he.isDate(e)) return e.toISOString();
            if (!s && he.isBlob(e)) throw new de("Blob is not supported. Use a Buffer instead.");
            return he.isArrayBuffer(e) || he.isTypedArray(e) ? s && "function" == typeof Blob ? new Blob([e]) : Buffer.from(e) : e
        }

        function c(e, r, o) {
            var s = e;
            if (e && !o && "object" === f(e)) if (he.endsWith(r, "{}")) r = n ? r : r.slice(0, -2), e = JSON.stringify(e); else if (he.isArray(e) && function (e) {
                return he.isArray(e) && !e.some(me)
            }(e) || (he.isFileList(e) || he.endsWith(r, "[]")) && (s = he.toArray(e))) return r = be(r), s.forEach((function (e, n) {
                !he.isUndefined(e) && null !== e && t.append(!0 === a ? ge([r], n, i) : null === a ? r : r + "[]", u(e))
            })), !1;
            return !!me(e) || (t.append(ge(o, r, i), u(e)), !1)
        }

        var l = [], p = Object.assign(we, {defaultVisitor: c, convertValue: u, isVisitable: me});
        if (!he.isObject(e)) throw new TypeError("data must be an object");
        return function e(r, n) {
            if (!he.isUndefined(r)) {
                if (-1 !== l.indexOf(r)) throw Error("Circular reference detected in " + n.join("."));
                l.push(r), he.forEach(r, (function (r, i) {
                    !0 === (!(he.isUndefined(r) || null === r) && o.call(t, r, he.isString(i) ? i.trim() : i, n, p)) && e(r, n ? n.concat(i) : [i])
                })), l.pop()
            }
        }(e), t
    }

    function Oe(e) {
        var t = {"!": "%21", "'": "%27", "(": "%28", ")": "%29", "~": "%7E", "%20": "+", "%00": "\0"};
        return encodeURIComponent(e).replace(/[!'()~]|%20|%00/g, (function (e) {
            return t[e]
        }))
    }

    function Se(e, t) {
        this._pairs = [], e && Ee(e, this, t)
    }

    var xe = Se.prototype;

    function Re(e) {
        return encodeURIComponent(e).replace(/%3A/gi, ":").replace(/%24/g, "$").replace(/%2C/gi, ",").replace(/%20/g, "+").replace(/%5B/gi, "[").replace(/%5D/gi, "]")
    }

    function ke(e, t, r) {
        if (!t) return e;
        var n = r && r.encode || Re;
        he.isFunction(r) && (r = {serialize: r});
        var o, i = r && r.serialize;
        if (o = i ? i(t, r) : he.isURLSearchParams(t) ? t.toString() : new Se(t, r).toString(n)) {
            var a = e.indexOf("#");
            -1 !== a && (e = e.slice(0, a)), e += (-1 === e.indexOf("?") ? "?" : "&") + o
        }
        return e
    }

    xe.append = function (e, t) {
        this._pairs.push([e, t])
    }, xe.toString = function (e) {
        var t = e ? function (t) {
            return e.call(this, t, Oe)
        } : Oe;
        return this._pairs.map((function (e) {
            return t(e[0]) + "=" + t(e[1])
        }), "").join("&")
    };
    var Te = function () {
            function e() {
                d(this, e), this.handlers = []
            }

            return y(e, [{
                key: "use", value: function (e, t, r) {
                    return this.handlers.push({
                        fulfilled: e,
                        rejected: t,
                        synchronous: !!r && r.synchronous,
                        runWhen: r ? r.runWhen : null
                    }), this.handlers.length - 1
                }
            }, {
                key: "eject", value: function (e) {
                    this.handlers[e] && (this.handlers[e] = null)
                }
            }, {
                key: "clear", value: function () {
                    this.handlers && (this.handlers = [])
                }
            }, {
                key: "forEach", value: function (e) {
                    he.forEach(this.handlers, (function (t) {
                        null !== t && e(t)
                    }))
                }
            }]), e
        }(), je = {silentJSONParsing: !0, forcedJSONParsing: !0, clarifyTimeoutError: !1}, Ae = {
            isBrowser: !0,
            classes: {
                URLSearchParams: "undefined" != typeof URLSearchParams ? URLSearchParams : Se,
                FormData: "undefined" != typeof FormData ? FormData : null,
                Blob: "undefined" != typeof Blob ? Blob : null
            },
            protocols: ["http", "https", "file", "blob", "url", "data"]
        }, Pe = "undefined" != typeof window && "undefined" != typeof document,
        Le = "object" === ("undefined" == typeof navigator ? "undefined" : f(navigator)) && navigator || void 0,
        Ne = Pe && (!Le || ["ReactNative", "NativeScript", "NS"].indexOf(Le.product) < 0),
        _e = "undefined" != typeof WorkerGlobalScope && self instanceof WorkerGlobalScope && "function" == typeof self.importScripts,
        Ce = Pe && window.location.href || "http://localhost", Ue = s(s({}, Object.freeze({
            __proto__: null,
            hasBrowserEnv: Pe,
            hasStandardBrowserWebWorkerEnv: _e,
            hasStandardBrowserEnv: Ne,
            navigator: Le,
            origin: Ce
        })), Ae);

    function Fe(e) {
        function t(e, r, n, o) {
            var i = e[o++];
            if ("__proto__" === i) return !0;
            var a = Number.isFinite(+i), s = o >= e.length;
            return i = !i && he.isArray(n) ? n.length : i, s ? (he.hasOwnProp(n, i) ? n[i] = [n[i], r] : n[i] = r, !a) : (n[i] && he.isObject(n[i]) || (n[i] = []), t(e, r, n[i], o) && he.isArray(n[i]) && (n[i] = function (e) {
                var t, r, n = {}, o = Object.keys(e), i = o.length;
                for (t = 0; t < i; t++) n[r = o[t]] = e[r];
                return n
            }(n[i])), !a)
        }

        if (he.isFormData(e) && he.isFunction(e.entries)) {
            var r = {};
            return he.forEachEntry(e, (function (e, n) {
                t(function (e) {
                    return he.matchAll(/\w+|\[(\w*)]/g, e).map((function (e) {
                        return "[]" === e[0] ? "" : e[1] || e[0]
                    }))
                }(e), n, r, 0)
            })), r
        }
        return null
    }

    var Be = {
        transitional: je,
        adapter: ["xhr", "http", "fetch"],
        transformRequest: [function (e, t) {
            var r, n = t.getContentType() || "", o = n.indexOf("application/json") > -1, i = he.isObject(e);
            if (i && he.isHTMLForm(e) && (e = new FormData(e)), he.isFormData(e)) return o ? JSON.stringify(Fe(e)) : e;
            if (he.isArrayBuffer(e) || he.isBuffer(e) || he.isStream(e) || he.isFile(e) || he.isBlob(e) || he.isReadableStream(e)) return e;
            if (he.isArrayBufferView(e)) return e.buffer;
            if (he.isURLSearchParams(e)) return t.setContentType("application/x-www-form-urlencoded;charset=utf-8", !1), e.toString();
            if (i) {
                if (n.indexOf("application/x-www-form-urlencoded") > -1) return function (e, t) {
                    return Ee(e, new Ue.classes.URLSearchParams, Object.assign({
                        visitor: function (e, t, r, n) {
                            return Ue.isNode && he.isBuffer(e) ? (this.append(t, e.toString("base64")), !1) : n.defaultVisitor.apply(this, arguments)
                        }
                    }, t))
                }(e, this.formSerializer).toString();
                if ((r = he.isFileList(e)) || n.indexOf("multipart/form-data") > -1) {
                    var a = this.env && this.env.FormData;
                    return Ee(r ? {"files[]": e} : e, a && new a, this.formSerializer)
                }
            }
            return i || o ? (t.setContentType("application/json", !1), function (e, t, r) {
                if (he.isString(e)) try {
                    return (t || JSON.parse)(e), he.trim(e)
                } catch (e) {
                    if ("SyntaxError" !== e.name) throw e
                }
                return (r || JSON.stringify)(e)
            }(e)) : e
        }],
        transformResponse: [function (e) {
            var t = this.transitional || Be.transitional, r = t && t.forcedJSONParsing,
                n = "json" === this.responseType;
            if (he.isResponse(e) || he.isReadableStream(e)) return e;
            if (e && he.isString(e) && (r && !this.responseType || n)) {
                var o = !(t && t.silentJSONParsing) && n;
                try {
                    return JSON.parse(e)
                } catch (e) {
                    if (o) {
                        if ("SyntaxError" === e.name) throw de.from(e, de.ERR_BAD_RESPONSE, this, null, this.response);
                        throw e
                    }
                }
            }
            return e
        }],
        timeout: 0,
        xsrfCookieName: "XSRF-TOKEN",
        xsrfHeaderName: "X-XSRF-TOKEN",
        maxContentLength: -1,
        maxBodyLength: -1,
        env: {FormData: Ue.classes.FormData, Blob: Ue.classes.Blob},
        validateStatus: function (e) {
            return e >= 200 && e < 300
        },
        headers: {common: {Accept: "application/json, text/plain, */*", "Content-Type": void 0}}
    };
    he.forEach(["delete", "get", "head", "post", "put", "patch"], (function (e) {
        Be.headers[e] = {}
    }));
    var De = Be,
        qe = he.toObjectSet(["age", "authorization", "content-length", "content-type", "etag", "expires", "from", "host", "if-modified-since", "if-unmodified-since", "last-modified", "location", "max-forwards", "proxy-authorization", "referer", "retry-after", "user-agent"]),
        Ie = Symbol("internals");

    function Me(e) {
        return e && String(e).trim().toLowerCase()
    }

    function ze(e) {
        return !1 === e || null == e ? e : he.isArray(e) ? e.map(ze) : String(e)
    }

    function He(e, t, r, n, o) {
        return he.isFunction(n) ? n.call(this, t, r) : (o && (t = r), he.isString(t) ? he.isString(n) ? -1 !== t.indexOf(n) : he.isRegExp(n) ? n.test(t) : void 0 : void 0)
    }

    var Je = function (e, t) {
        function r(e) {
            d(this, r), e && this.set(e)
        }

        return y(r, [{
            key: "set", value: function (e, t, r) {
                var n = this;

                function o(e, t, r) {
                    var o = Me(t);
                    if (!o) throw new Error("header name must be a non-empty string");
                    var i = he.findKey(n, o);
                    (!i || void 0 === n[i] || !0 === r || void 0 === r && !1 !== n[i]) && (n[i || t] = ze(e))
                }

                var i = function (e, t) {
                    return he.forEach(e, (function (e, r) {
                        return o(e, r, t)
                    }))
                };
                if (he.isPlainObject(e) || e instanceof this.constructor) i(e, t); else if (he.isString(e) && (e = e.trim()) && !/^[-_a-zA-Z0-9^`|~,!#$%&'*+.]+$/.test(e.trim())) i(function (e) {
                    var t, r, n, o = {};
                    return e && e.split("\n").forEach((function (e) {
                        n = e.indexOf(":"), t = e.substring(0, n).trim().toLowerCase(), r = e.substring(n + 1).trim(), !t || o[t] && qe[t] || ("set-cookie" === t ? o[t] ? o[t].push(r) : o[t] = [r] : o[t] = o[t] ? o[t] + ", " + r : r)
                    })), o
                }(e), t); else if (he.isHeaders(e)) {
                    var a, s = function (e, t) {
                        var r = "undefined" != typeof Symbol && e[Symbol.iterator] || e["@@iterator"];
                        if (!r) {
                            if (Array.isArray(e) || (r = O(e)) || t && e && "number" == typeof e.length) {
                                r && (e = r);
                                var n = 0, o = function () {
                                };
                                return {
                                    s: o, n: function () {
                                        return n >= e.length ? {done: !0} : {done: !1, value: e[n++]}
                                    }, e: function (e) {
                                        throw e
                                    }, f: o
                                }
                            }
                            throw new TypeError("Invalid attempt to iterate non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")
                        }
                        var i, a = !0, s = !1;
                        return {
                            s: function () {
                                r = r.call(e)
                            }, n: function () {
                                var e = r.next();
                                return a = e.done, e
                            }, e: function (e) {
                                s = !0, i = e
                            }, f: function () {
                                try {
                                    a || null == r.return || r.return()
                                } finally {
                                    if (s) throw i
                                }
                            }
                        }
                    }(e.entries());
                    try {
                        for (s.s(); !(a = s.n()).done;) {
                            var u = b(a.value, 2), c = u[0];
                            o(u[1], c, r)
                        }
                    } catch (e) {
                        s.e(e)
                    } finally {
                        s.f()
                    }
                } else null != e && o(t, e, r);
                return this
            }
        }, {
            key: "get", value: function (e, t) {
                if (e = Me(e)) {
                    var r = he.findKey(this, e);
                    if (r) {
                        var n = this[r];
                        if (!t) return n;
                        if (!0 === t) return function (e) {
                            for (var t, r = Object.create(null), n = /([^\s,;=]+)\s*(?:=\s*([^,;]+))?/g; t = n.exec(e);) r[t[1]] = t[2];
                            return r
                        }(n);
                        if (he.isFunction(t)) return t.call(this, n, r);
                        if (he.isRegExp(t)) return t.exec(n);
                        throw new TypeError("parser must be boolean|regexp|function")
                    }
                }
            }
        }, {
            key: "has", value: function (e, t) {
                if (e = Me(e)) {
                    var r = he.findKey(this, e);
                    return !(!r || void 0 === this[r] || t && !He(0, this[r], r, t))
                }
                return !1
            }
        }, {
            key: "delete", value: function (e, t) {
                var r = this, n = !1;

                function o(e) {
                    if (e = Me(e)) {
                        var o = he.findKey(r, e);
                        !o || t && !He(0, r[o], o, t) || (delete r[o], n = !0)
                    }
                }

                return he.isArray(e) ? e.forEach(o) : o(e), n
            }
        }, {
            key: "clear", value: function (e) {
                for (var t = Object.keys(this), r = t.length, n = !1; r--;) {
                    var o = t[r];
                    e && !He(0, this[o], o, e, !0) || (delete this[o], n = !0)
                }
                return n
            }
        }, {
            key: "normalize", value: function (e) {
                var t = this, r = {};
                return he.forEach(this, (function (n, o) {
                    var i = he.findKey(r, o);
                    if (i) return t[i] = ze(n), void delete t[o];
                    var a = e ? function (e) {
                        return e.trim().toLowerCase().replace(/([a-z\d])(\w*)/g, (function (e, t, r) {
                            return t.toUpperCase() + r
                        }))
                    }(o) : String(o).trim();
                    a !== o && delete t[o], t[a] = ze(n), r[a] = !0
                })), this
            }
        }, {
            key: "concat", value: function () {
                for (var e, t = arguments.length, r = new Array(t), n = 0; n < t; n++) r[n] = arguments[n];
                return (e = this.constructor).concat.apply(e, [this].concat(r))
            }
        }, {
            key: "toJSON", value: function (e) {
                var t = Object.create(null);
                return he.forEach(this, (function (r, n) {
                    null != r && !1 !== r && (t[n] = e && he.isArray(r) ? r.join(", ") : r)
                })), t
            }
        }, {
            key: Symbol.iterator, value: function () {
                return Object.entries(this.toJSON())[Symbol.iterator]()
            }
        }, {
            key: "toString", value: function () {
                return Object.entries(this.toJSON()).map((function (e) {
                    var t = b(e, 2);
                    return t[0] + ": " + t[1]
                })).join("\n")
            }
        }, {
            key: Symbol.toStringTag, get: function () {
                return "AxiosHeaders"
            }
        }], [{
            key: "from", value: function (e) {
                return e instanceof this ? e : new this(e)
            }
        }, {
            key: "concat", value: function (e) {
                for (var t = new this(e), r = arguments.length, n = new Array(r > 1 ? r - 1 : 0), o = 1; o < r; o++) n[o - 1] = arguments[o];
                return n.forEach((function (e) {
                    return t.set(e)
                })), t
            }
        }, {
            key: "accessor", value: function (e) {
                var t = (this[Ie] = this[Ie] = {accessors: {}}).accessors, r = this.prototype;

                function n(e) {
                    var n = Me(e);
                    t[n] || (!function (e, t) {
                        var r = he.toCamelCase(" " + t);
                        ["get", "set", "has"].forEach((function (n) {
                            Object.defineProperty(e, n + r, {
                                value: function (e, r, o) {
                                    return this[n].call(this, t, e, r, o)
                                }, configurable: !0
                            })
                        }))
                    }(r, e), t[n] = !0)
                }

                return he.isArray(e) ? e.forEach(n) : n(e), this
            }
        }]), r
    }();
    Je.accessor(["Content-Type", "Content-Length", "Accept", "Accept-Encoding", "User-Agent", "Authorization"]), he.reduceDescriptors(Je.prototype, (function (e, t) {
        var r = e.value, n = t[0].toUpperCase() + t.slice(1);
        return {
            get: function () {
                return r
            }, set: function (e) {
                this[n] = e
            }
        }
    })), he.freezeMethods(Je);
    var We = Je;

    function Ke(e, t) {
        var r = this || De, n = t || r, o = We.from(n.headers), i = n.data;
        return he.forEach(e, (function (e) {
            i = e.call(r, i, o.normalize(), t ? t.status : void 0)
        })), o.normalize(), i
    }

    function Ve(e) {
        return !(!e || !e.__CANCEL__)
    }

    function Ge(e, t, r) {
        de.call(this, null == e ? "canceled" : e, de.ERR_CANCELED, t, r), this.name = "CanceledError"
    }

    function Xe(e, t, r) {
        var n = r.config.validateStatus;
        r.status && n && !n(r.status) ? t(new de("Request failed with status code " + r.status, [de.ERR_BAD_REQUEST, de.ERR_BAD_RESPONSE][Math.floor(r.status / 100) - 4], r.config, r.request, r)) : e(r)
    }

    function $e(e, t) {
        e = e || 10;
        var r, n = new Array(e), o = new Array(e), i = 0, a = 0;
        return t = void 0 !== t ? t : 1e3, function (s) {
            var u = Date.now(), c = o[a];
            r || (r = u), n[i] = s, o[i] = u;
            for (var f = a, l = 0; f !== i;) l += n[f++], f %= e;
            if ((i = (i + 1) % e) === a && (a = (a + 1) % e), !(u - r < t)) {
                var p = c && u - c;
                return p ? Math.round(1e3 * l / p) : void 0
            }
        }
    }

    function Ye(e, t) {
        var r, n, o = 0, i = 1e3 / t, a = function (t) {
            var i = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : Date.now();
            o = i, r = null, n && (clearTimeout(n), n = null), e.apply(null, t)
        };
        return [function () {
            for (var e = Date.now(), t = e - o, s = arguments.length, u = new Array(s), c = 0; c < s; c++) u[c] = arguments[c];
            t >= i ? a(u, e) : (r = u, n || (n = setTimeout((function () {
                n = null, a(r)
            }), i - t)))
        }, function () {
            return r && a(r)
        }]
    }

    he.inherits(Ge, de, {__CANCEL__: !0});
    var Qe = function (e, t) {
        var r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : 3, n = 0, o = $e(50, 250);
        return Ye((function (r) {
            var i = r.loaded, a = r.lengthComputable ? r.total : void 0, s = i - n, u = o(s);
            n = i;
            var c = m({
                loaded: i,
                total: a,
                progress: a ? i / a : void 0,
                bytes: s,
                rate: u || void 0,
                estimated: u && a && i <= a ? (a - i) / u : void 0,
                event: r,
                lengthComputable: null != a
            }, t ? "download" : "upload", !0);
            e(c)
        }), r)
    }, Ze = function (e, t) {
        var r = null != e;
        return [function (n) {
            return t[0]({lengthComputable: r, total: e, loaded: n})
        }, t[1]]
    }, et = function (e) {
        return function () {
            for (var t = arguments.length, r = new Array(t), n = 0; n < t; n++) r[n] = arguments[n];
            return he.asap((function () {
                return e.apply(void 0, r)
            }))
        }
    }, tt = Ue.hasStandardBrowserEnv ? function (e, t) {
        return function (r) {
            return r = new URL(r, Ue.origin), e.protocol === r.protocol && e.host === r.host && (t || e.port === r.port)
        }
    }(new URL(Ue.origin), Ue.navigator && /(msie|trident)/i.test(Ue.navigator.userAgent)) : function () {
        return !0
    }, rt = Ue.hasStandardBrowserEnv ? {
        write: function (e, t, r, n, o, i) {
            var a = [e + "=" + encodeURIComponent(t)];
            he.isNumber(r) && a.push("expires=" + new Date(r).toGMTString()), he.isString(n) && a.push("path=" + n), he.isString(o) && a.push("domain=" + o), !0 === i && a.push("secure"), document.cookie = a.join("; ")
        }, read: function (e) {
            var t = document.cookie.match(new RegExp("(^|;\\s*)(" + e + ")=([^;]*)"));
            return t ? decodeURIComponent(t[3]) : null
        }, remove: function (e) {
            this.write(e, "", Date.now() - 864e5)
        }
    } : {
        write: function () {
        }, read: function () {
            return null
        }, remove: function () {
        }
    };

    function nt(e, t, r) {
        var n = !/^([a-z][a-z\d+\-.]*:)?\/\//i.test(t);
        return e && (n || 0 == r) ? function (e, t) {
            return t ? e.replace(/\/?\/$/, "") + "/" + t.replace(/^\/+/, "") : e
        }(e, t) : t
    }

    var ot = function (e) {
        return e instanceof We ? s({}, e) : e
    };

    function it(e, t) {
        t = t || {};
        var r = {};

        function n(e, t, r, n) {
            return he.isPlainObject(e) && he.isPlainObject(t) ? he.merge.call({caseless: n}, e, t) : he.isPlainObject(t) ? he.merge({}, t) : he.isArray(t) ? t.slice() : t
        }

        function o(e, t, r, o) {
            return he.isUndefined(t) ? he.isUndefined(e) ? void 0 : n(void 0, e, 0, o) : n(e, t, 0, o)
        }

        function i(e, t) {
            if (!he.isUndefined(t)) return n(void 0, t)
        }

        function a(e, t) {
            return he.isUndefined(t) ? he.isUndefined(e) ? void 0 : n(void 0, e) : n(void 0, t)
        }

        function s(r, o, i) {
            return i in t ? n(r, o) : i in e ? n(void 0, r) : void 0
        }

        var u = {
            url: i,
            method: i,
            data: i,
            baseURL: a,
            transformRequest: a,
            transformResponse: a,
            paramsSerializer: a,
            timeout: a,
            timeoutMessage: a,
            withCredentials: a,
            withXSRFToken: a,
            adapter: a,
            responseType: a,
            xsrfCookieName: a,
            xsrfHeaderName: a,
            onUploadProgress: a,
            onDownloadProgress: a,
            decompress: a,
            maxContentLength: a,
            maxBodyLength: a,
            beforeRedirect: a,
            transport: a,
            httpAgent: a,
            httpsAgent: a,
            cancelToken: a,
            socketPath: a,
            responseEncoding: a,
            validateStatus: s,
            headers: function (e, t, r) {
                return o(ot(e), ot(t), 0, !0)
            }
        };
        return he.forEach(Object.keys(Object.assign({}, e, t)), (function (n) {
            var i = u[n] || o, a = i(e[n], t[n], n);
            he.isUndefined(a) && i !== s || (r[n] = a)
        })), r
    }

    var at, st, ut = function (e) {
            var t, r, n = it({}, e), o = n.data, i = n.withXSRFToken, a = n.xsrfHeaderName, s = n.xsrfCookieName,
                u = n.headers, c = n.auth;
            if (n.headers = u = We.from(u), n.url = ke(nt(n.baseURL, n.url, n.allowAbsoluteUrls), e.params, e.paramsSerializer), c && u.set("Authorization", "Basic " + btoa((c.username || "") + ":" + (c.password ? unescape(encodeURIComponent(c.password)) : ""))), he.isFormData(o)) if (Ue.hasStandardBrowserEnv || Ue.hasStandardBrowserWebWorkerEnv) u.setContentType(void 0); else if (!1 !== (t = u.getContentType())) {
                var f = t ? t.split(";").map((function (e) {
                    return e.trim()
                })).filter(Boolean) : [], l = w(r = f) || E(r) || O(r) || x(), p = l[0], h = l.slice(1);
                u.setContentType([p || "multipart/form-data"].concat(g(h)).join("; "))
            }
            if (Ue.hasStandardBrowserEnv && (i && he.isFunction(i) && (i = i(n)), i || !1 !== i && tt(n.url))) {
                var d = a && s && rt.read(s);
                d && u.set(a, d)
            }
            return n
        }, ct = "undefined" != typeof XMLHttpRequest && function (e) {
            return new Promise((function (t, r) {
                var n, o, i, a, s, u = ut(e), c = u.data, f = We.from(u.headers).normalize(), l = u.responseType,
                    p = u.onUploadProgress, h = u.onDownloadProgress;

                function d() {
                    a && a(), s && s(), u.cancelToken && u.cancelToken.unsubscribe(n), u.signal && u.signal.removeEventListener("abort", n)
                }

                var v = new XMLHttpRequest;

                function y() {
                    if (v) {
                        var n = We.from("getAllResponseHeaders" in v && v.getAllResponseHeaders());
                        Xe((function (e) {
                            t(e), d()
                        }), (function (e) {
                            r(e), d()
                        }), {
                            data: l && "text" !== l && "json" !== l ? v.response : v.responseText,
                            status: v.status,
                            statusText: v.statusText,
                            headers: n,
                            config: e,
                            request: v
                        }), v = null
                    }
                }

                if (v.open(u.method.toUpperCase(), u.url, !0), v.timeout = u.timeout, "onloadend" in v ? v.onloadend = y : v.onreadystatechange = function () {
                    v && 4 === v.readyState && (0 !== v.status || v.responseURL && 0 === v.responseURL.indexOf("file:")) && setTimeout(y)
                }, v.onabort = function () {
                    v && (r(new de("Request aborted", de.ECONNABORTED, e, v)), v = null)
                }, v.onerror = function () {
                    r(new de("Network Error", de.ERR_NETWORK, e, v)), v = null
                }, v.ontimeout = function () {
                    var t = u.timeout ? "timeout of " + u.timeout + "ms exceeded" : "timeout exceeded",
                        n = u.transitional || je;
                    u.timeoutErrorMessage && (t = u.timeoutErrorMessage), r(new de(t, n.clarifyTimeoutError ? de.ETIMEDOUT : de.ECONNABORTED, e, v)), v = null
                }, void 0 === c && f.setContentType(null), "setRequestHeader" in v && he.forEach(f.toJSON(), (function (e, t) {
                    v.setRequestHeader(t, e)
                })), he.isUndefined(u.withCredentials) || (v.withCredentials = !!u.withCredentials), l && "json" !== l && (v.responseType = u.responseType), h) {
                    var m = b(Qe(h, !0), 2);
                    i = m[0], s = m[1], v.addEventListener("progress", i)
                }
                if (p && v.upload) {
                    var g = b(Qe(p), 2);
                    o = g[0], a = g[1], v.upload.addEventListener("progress", o), v.upload.addEventListener("loadend", a)
                }
                (u.cancelToken || u.signal) && (n = function (t) {
                    v && (r(!t || t.type ? new Ge(null, e, v) : t), v.abort(), v = null)
                }, u.cancelToken && u.cancelToken.subscribe(n), u.signal && (u.signal.aborted ? n() : u.signal.addEventListener("abort", n)));
                var w, E, O = (w = u.url, (E = /^([-+\w]{1,25})(:?\/\/|:)/.exec(w)) && E[1] || "");
                O && -1 === Ue.protocols.indexOf(O) ? r(new de("Unsupported protocol " + O + ":", de.ERR_BAD_REQUEST, e)) : v.send(c || null)
            }))
        }, ft = function (e, t) {
            var r = (e = e ? e.filter(Boolean) : []).length;
            if (t || r) {
                var n, o = new AbortController, i = function (e) {
                    if (!n) {
                        n = !0, s();
                        var t = e instanceof Error ? e : this.reason;
                        o.abort(t instanceof de ? t : new Ge(t instanceof Error ? t.message : t))
                    }
                }, a = t && setTimeout((function () {
                    a = null, i(new de("timeout ".concat(t, " of ms exceeded"), de.ETIMEDOUT))
                }), t), s = function () {
                    e && (a && clearTimeout(a), a = null, e.forEach((function (e) {
                        e.unsubscribe ? e.unsubscribe(i) : e.removeEventListener("abort", i)
                    })), e = null)
                };
                e.forEach((function (e) {
                    return e.addEventListener("abort", i)
                }));
                var u = o.signal;
                return u.unsubscribe = function () {
                    return he.asap(s)
                }, u
            }
        }, lt = u().mark((function e(t, r) {
            var n, o, i;
            return u().wrap((function (e) {
                for (; ;) switch (e.prev = e.next) {
                    case 0:
                        if (n = t.byteLength, r && !(n < r)) {
                            e.next = 5;
                            break
                        }
                        return e.next = 4, t;
                    case 4:
                        return e.abrupt("return");
                    case 5:
                        o = 0;
                    case 6:
                        if (!(o < n)) {
                            e.next = 13;
                            break
                        }
                        return i = o + r, e.next = 10, t.slice(o, i);
                    case 10:
                        o = i, e.next = 6;
                        break;
                    case 13:
                    case"end":
                        return e.stop()
                }
            }), e)
        })), pt = function () {
            var e = l(u().mark((function e(t, o) {
                var a, s, c, f, l, p;
                return u().wrap((function (e) {
                    for (; ;) switch (e.prev = e.next) {
                        case 0:
                            a = !1, s = !1, e.prev = 2, f = n(ht(t));
                        case 4:
                            return e.next = 6, i(f.next());
                        case 6:
                            if (!(a = !(l = e.sent).done)) {
                                e.next = 12;
                                break
                            }
                            return p = l.value, e.delegateYield(r(n(lt(p, o))), "t0", 9);
                        case 9:
                            a = !1, e.next = 4;
                            break;
                        case 12:
                            e.next = 18;
                            break;
                        case 14:
                            e.prev = 14, e.t1 = e.catch(2), s = !0, c = e.t1;
                        case 18:
                            if (e.prev = 18, e.prev = 19, !a || null == f.return) {
                                e.next = 23;
                                break
                            }
                            return e.next = 23, i(f.return());
                        case 23:
                            if (e.prev = 23, !s) {
                                e.next = 26;
                                break
                            }
                            throw c;
                        case 26:
                            return e.finish(23);
                        case 27:
                            return e.finish(18);
                        case 28:
                        case"end":
                            return e.stop()
                    }
                }), e, null, [[2, 14, 18, 28], [19, , 23, 27]])
            })));
            return function (t, r) {
                return e.apply(this, arguments)
            }
        }(), ht = function () {
            var e = l(u().mark((function e(t) {
                var o, a, s, c;
                return u().wrap((function (e) {
                    for (; ;) switch (e.prev = e.next) {
                        case 0:
                            if (!t[Symbol.asyncIterator]) {
                                e.next = 3;
                                break
                            }
                            return e.delegateYield(r(n(t)), "t0", 2);
                        case 2:
                            return e.abrupt("return");
                        case 3:
                            o = t.getReader(), e.prev = 4;
                        case 5:
                            return e.next = 7, i(o.read());
                        case 7:
                            if (a = e.sent, s = a.done, c = a.value, !s) {
                                e.next = 12;
                                break
                            }
                            return e.abrupt("break", 16);
                        case 12:
                            return e.next = 14, c;
                        case 14:
                            e.next = 5;
                            break;
                        case 16:
                            return e.prev = 16, e.next = 19, i(o.cancel());
                        case 19:
                            return e.finish(16);
                        case 20:
                        case"end":
                            return e.stop()
                    }
                }), e, null, [[4, , 16, 20]])
            })));
            return function (t) {
                return e.apply(this, arguments)
            }
        }(), dt = function (e, t, r, n) {
            var o, i = pt(e, t), a = 0, s = function (e) {
                o || (o = !0, n && n(e))
            };
            return new ReadableStream({
                pull: function (e) {
                    return h(u().mark((function t() {
                        var n, o, c, f, l;
                        return u().wrap((function (t) {
                            for (; ;) switch (t.prev = t.next) {
                                case 0:
                                    return t.prev = 0, t.next = 3, i.next();
                                case 3:
                                    if (n = t.sent, o = n.done, c = n.value, !o) {
                                        t.next = 10;
                                        break
                                    }
                                    return s(), e.close(), t.abrupt("return");
                                case 10:
                                    f = c.byteLength, r && (l = a += f, r(l)), e.enqueue(new Uint8Array(c)), t.next = 19;
                                    break;
                                case 15:
                                    throw t.prev = 15, t.t0 = t.catch(0), s(t.t0), t.t0;
                                case 19:
                                case"end":
                                    return t.stop()
                            }
                        }), t, null, [[0, 15]])
                    })))()
                }, cancel: function (e) {
                    return s(e), i.return()
                }
            }, {highWaterMark: 2})
        }, vt = "function" == typeof fetch && "function" == typeof Request && "function" == typeof Response,
        yt = vt && "function" == typeof ReadableStream,
        mt = vt && ("function" == typeof TextEncoder ? (at = new TextEncoder, function (e) {
            return at.encode(e)
        }) : function () {
            var e = h(u().mark((function e(t) {
                return u().wrap((function (e) {
                    for (; ;) switch (e.prev = e.next) {
                        case 0:
                            return e.t0 = Uint8Array, e.next = 3, new Response(t).arrayBuffer();
                        case 3:
                            return e.t1 = e.sent, e.abrupt("return", new e.t0(e.t1));
                        case 5:
                        case"end":
                            return e.stop()
                    }
                }), e)
            })));
            return function (t) {
                return e.apply(this, arguments)
            }
        }()), bt = function (e) {
            try {
                for (var t = arguments.length, r = new Array(t > 1 ? t - 1 : 0), n = 1; n < t; n++) r[n - 1] = arguments[n];
                return !!e.apply(void 0, r)
            } catch (e) {
                return !1
            }
        }, gt = yt && bt((function () {
            var e = !1, t = new Request(Ue.origin, {
                body: new ReadableStream, method: "POST", get duplex() {
                    return e = !0, "half"
                }
            }).headers.has("Content-Type");
            return e && !t
        })), wt = yt && bt((function () {
            return he.isReadableStream(new Response("").body)
        })), Et = {
            stream: wt && function (e) {
                return e.body
            }
        };
    vt && (st = new Response, ["text", "arrayBuffer", "blob", "formData", "stream"].forEach((function (e) {
        !Et[e] && (Et[e] = he.isFunction(st[e]) ? function (t) {
            return t[e]()
        } : function (t, r) {
            throw new de("Response type '".concat(e, "' is not supported"), de.ERR_NOT_SUPPORT, r)
        })
    })));
    var Ot = function () {
        var e = h(u().mark((function e(t) {
            var r;
            return u().wrap((function (e) {
                for (; ;) switch (e.prev = e.next) {
                    case 0:
                        if (null != t) {
                            e.next = 2;
                            break
                        }
                        return e.abrupt("return", 0);
                    case 2:
                        if (!he.isBlob(t)) {
                            e.next = 4;
                            break
                        }
                        return e.abrupt("return", t.size);
                    case 4:
                        if (!he.isSpecCompliantForm(t)) {
                            e.next = 9;
                            break
                        }
                        return r = new Request(Ue.origin, {method: "POST", body: t}), e.next = 8, r.arrayBuffer();
                    case 8:
                    case 15:
                        return e.abrupt("return", e.sent.byteLength);
                    case 9:
                        if (!he.isArrayBufferView(t) && !he.isArrayBuffer(t)) {
                            e.next = 11;
                            break
                        }
                        return e.abrupt("return", t.byteLength);
                    case 11:
                        if (he.isURLSearchParams(t) && (t += ""), !he.isString(t)) {
                            e.next = 16;
                            break
                        }
                        return e.next = 15, mt(t);
                    case 16:
                    case"end":
                        return e.stop()
                }
            }), e)
        })));
        return function (t) {
            return e.apply(this, arguments)
        }
    }(), St = function () {
        var e = h(u().mark((function e(t, r) {
            var n;
            return u().wrap((function (e) {
                for (; ;) switch (e.prev = e.next) {
                    case 0:
                        return n = he.toFiniteNumber(t.getContentLength()), e.abrupt("return", null == n ? Ot(r) : n);
                    case 2:
                    case"end":
                        return e.stop()
                }
            }), e)
        })));
        return function (t, r) {
            return e.apply(this, arguments)
        }
    }(), xt = vt && function () {
        var e = h(u().mark((function e(t) {
            var r, n, o, i, a, c, f, l, p, h, d, v, y, m, g, w, E, O, S, x, R, k, T, j, A, P, L, N, _, C, U, F, B, D;
            return u().wrap((function (e) {
                for (; ;) switch (e.prev = e.next) {
                    case 0:
                        if (r = ut(t), n = r.url, o = r.method, i = r.data, a = r.signal, c = r.cancelToken, f = r.timeout, l = r.onDownloadProgress, p = r.onUploadProgress, h = r.responseType, d = r.headers, v = r.withCredentials, y = void 0 === v ? "same-origin" : v, m = r.fetchOptions, h = h ? (h + "").toLowerCase() : "text", g = ft([a, c && c.toAbortSignal()], f), E = g && g.unsubscribe && function () {
                            g.unsubscribe()
                        }, e.prev = 4, e.t0 = p && gt && "get" !== o && "head" !== o, !e.t0) {
                            e.next = 11;
                            break
                        }
                        return e.next = 9, St(d, i);
                    case 9:
                        e.t1 = O = e.sent, e.t0 = 0 !== e.t1;
                    case 11:
                        if (!e.t0) {
                            e.next = 15;
                            break
                        }
                        S = new Request(n, {
                            method: "POST",
                            body: i,
                            duplex: "half"
                        }), he.isFormData(i) && (x = S.headers.get("content-type")) && d.setContentType(x), S.body && (R = Ze(O, Qe(et(p))), k = b(R, 2), T = k[0], j = k[1], i = dt(S.body, 65536, T, j));
                    case 15:
                        return he.isString(y) || (y = y ? "include" : "omit"), A = "credentials" in Request.prototype, w = new Request(n, s(s({}, m), {}, {
                            signal: g,
                            method: o.toUpperCase(),
                            headers: d.normalize().toJSON(),
                            body: i,
                            duplex: "half",
                            credentials: A ? y : void 0
                        })), e.next = 20, fetch(w);
                    case 20:
                        return P = e.sent, L = wt && ("stream" === h || "response" === h), wt && (l || L && E) && (N = {}, ["status", "statusText", "headers"].forEach((function (e) {
                            N[e] = P[e]
                        })), _ = he.toFiniteNumber(P.headers.get("content-length")), C = l && Ze(_, Qe(et(l), !0)) || [], U = b(C, 2), F = U[0], B = U[1], P = new Response(dt(P.body, 65536, F, (function () {
                            B && B(), E && E()
                        })), N)), h = h || "text", e.next = 26, Et[he.findKey(Et, h) || "text"](P, t);
                    case 26:
                        return D = e.sent, !L && E && E(), e.next = 30, new Promise((function (e, r) {
                            Xe(e, r, {
                                data: D,
                                headers: We.from(P.headers),
                                status: P.status,
                                statusText: P.statusText,
                                config: t,
                                request: w
                            })
                        }));
                    case 30:
                        return e.abrupt("return", e.sent);
                    case 33:
                        if (e.prev = 33, e.t2 = e.catch(4), E && E(), !e.t2 || "TypeError" !== e.t2.name || !/fetch/i.test(e.t2.message)) {
                            e.next = 38;
                            break
                        }
                        throw Object.assign(new de("Network Error", de.ERR_NETWORK, t, w), {cause: e.t2.cause || e.t2});
                    case 38:
                        throw de.from(e.t2, e.t2 && e.t2.code, t, w);
                    case 39:
                    case"end":
                        return e.stop()
                }
            }), e, null, [[4, 33]])
        })));
        return function (t) {
            return e.apply(this, arguments)
        }
    }(), Rt = {http: null, xhr: ct, fetch: xt};
    he.forEach(Rt, (function (e, t) {
        if (e) {
            try {
                Object.defineProperty(e, "name", {value: t})
            } catch (e) {
            }
            Object.defineProperty(e, "adapterName", {value: t})
        }
    }));
    var kt = function (e) {
        return "- ".concat(e)
    }, Tt = function (e) {
        return he.isFunction(e) || null === e || !1 === e
    }, jt = function (e) {
        for (var t, r, n = (e = he.isArray(e) ? e : [e]).length, o = {}, i = 0; i < n; i++) {
            var a = void 0;
            if (r = t = e[i], !Tt(t) && void 0 === (r = Rt[(a = String(t)).toLowerCase()])) throw new de("Unknown adapter '".concat(a, "'"));
            if (r) break;
            o[a || "#" + i] = r
        }
        if (!r) {
            var s = Object.entries(o).map((function (e) {
                var t = b(e, 2), r = t[0], n = t[1];
                return "adapter ".concat(r, " ") + (!1 === n ? "is not supported by the environment" : "is not available in the build")
            }));
            throw new de("There is no suitable adapter to dispatch the request " + (n ? s.length > 1 ? "since :\n" + s.map(kt).join("\n") : " " + kt(s[0]) : "as no adapter specified"), "ERR_NOT_SUPPORT")
        }
        return r
    };

    function At(e) {
        if (e.cancelToken && e.cancelToken.throwIfRequested(), e.signal && e.signal.aborted) throw new Ge(null, e)
    }

    function Pt(e) {
        return At(e), e.headers = We.from(e.headers), e.data = Ke.call(e, e.transformRequest), -1 !== ["post", "put", "patch"].indexOf(e.method) && e.headers.setContentType("application/x-www-form-urlencoded", !1), jt(e.adapter || De.adapter)(e).then((function (t) {
            return At(e), t.data = Ke.call(e, e.transformResponse, t), t.headers = We.from(t.headers), t
        }), (function (t) {
            return Ve(t) || (At(e), t && t.response && (t.response.data = Ke.call(e, e.transformResponse, t.response), t.response.headers = We.from(t.response.headers))), Promise.reject(t)
        }))
    }

    var Lt = "1.8.4", Nt = {};
    ["object", "boolean", "number", "function", "string", "symbol"].forEach((function (e, t) {
        Nt[e] = function (r) {
            return f(r) === e || "a" + (t < 1 ? "n " : " ") + e
        }
    }));
    var _t = {};
    Nt.transitional = function (e, t, r) {
        function n(e, t) {
            return "[Axios v1.8.4] Transitional option '" + e + "'" + t + (r ? ". " + r : "")
        }

        return function (r, o, i) {
            if (!1 === e) throw new de(n(o, " has been removed" + (t ? " in " + t : "")), de.ERR_DEPRECATED);
            return t && !_t[o] && (_t[o] = !0, console.warn(n(o, " has been deprecated since v" + t + " and will be removed in the near future"))), !e || e(r, o, i)
        }
    }, Nt.spelling = function (e) {
        return function (t, r) {
            return console.warn("".concat(r, " is likely a misspelling of ").concat(e)), !0
        }
    };
    var Ct = {
        assertOptions: function (e, t, r) {
            if ("object" !== f(e)) throw new de("options must be an object", de.ERR_BAD_OPTION_VALUE);
            for (var n = Object.keys(e), o = n.length; o-- > 0;) {
                var i = n[o], a = t[i];
                if (a) {
                    var s = e[i], u = void 0 === s || a(s, i, e);
                    if (!0 !== u) throw new de("option " + i + " must be " + u, de.ERR_BAD_OPTION_VALUE)
                } else if (!0 !== r) throw new de("Unknown option " + i, de.ERR_BAD_OPTION)
            }
        }, validators: Nt
    }, Ut = Ct.validators, Ft = function () {
        function e(t) {
            d(this, e), this.defaults = t, this.interceptors = {request: new Te, response: new Te}
        }

        var t;
        return y(e, [{
            key: "request", value: (t = h(u().mark((function e(t, r) {
                var n, o;
                return u().wrap((function (e) {
                    for (; ;) switch (e.prev = e.next) {
                        case 0:
                            return e.prev = 0, e.next = 3, this._request(t, r);
                        case 3:
                            return e.abrupt("return", e.sent);
                        case 6:
                            if (e.prev = 6, e.t0 = e.catch(0), e.t0 instanceof Error) {
                                n = {}, Error.captureStackTrace ? Error.captureStackTrace(n) : n = new Error, o = n.stack ? n.stack.replace(/^.+\n/, "") : "";
                                try {
                                    e.t0.stack ? o && !String(e.t0.stack).endsWith(o.replace(/^.+\n.+\n/, "")) && (e.t0.stack += "\n" + o) : e.t0.stack = o
                                } catch (e) {
                                }
                            }
                            throw e.t0;
                        case 10:
                        case"end":
                            return e.stop()
                    }
                }), e, this, [[0, 6]])
            }))), function (e, r) {
                return t.apply(this, arguments)
            })
        }, {
            key: "_request", value: function (e, t) {
                "string" == typeof e ? (t = t || {}).url = e : t = e || {};
                var r = t = it(this.defaults, t), n = r.transitional, o = r.paramsSerializer, i = r.headers;
                void 0 !== n && Ct.assertOptions(n, {
                    silentJSONParsing: Ut.transitional(Ut.boolean),
                    forcedJSONParsing: Ut.transitional(Ut.boolean),
                    clarifyTimeoutError: Ut.transitional(Ut.boolean)
                }, !1), null != o && (he.isFunction(o) ? t.paramsSerializer = {serialize: o} : Ct.assertOptions(o, {
                    encode: Ut.function,
                    serialize: Ut.function
                }, !0)), void 0 !== t.allowAbsoluteUrls || (void 0 !== this.defaults.allowAbsoluteUrls ? t.allowAbsoluteUrls = this.defaults.allowAbsoluteUrls : t.allowAbsoluteUrls = !0), Ct.assertOptions(t, {
                    baseUrl: Ut.spelling("baseURL"),
                    withXsrfToken: Ut.spelling("withXSRFToken")
                }, !0), t.method = (t.method || this.defaults.method || "get").toLowerCase();
                var a = i && he.merge(i.common, i[t.method]);
                i && he.forEach(["delete", "get", "head", "post", "put", "patch", "common"], (function (e) {
                    delete i[e]
                })), t.headers = We.concat(a, i);
                var s = [], u = !0;
                this.interceptors.request.forEach((function (e) {
                    "function" == typeof e.runWhen && !1 === e.runWhen(t) || (u = u && e.synchronous, s.unshift(e.fulfilled, e.rejected))
                }));
                var c, f = [];
                this.interceptors.response.forEach((function (e) {
                    f.push(e.fulfilled, e.rejected)
                }));
                var l, p = 0;
                if (!u) {
                    var h = [Pt.bind(this), void 0];
                    for (h.unshift.apply(h, s), h.push.apply(h, f), l = h.length, c = Promise.resolve(t); p < l;) c = c.then(h[p++], h[p++]);
                    return c
                }
                l = s.length;
                var d = t;
                for (p = 0; p < l;) {
                    var v = s[p++], y = s[p++];
                    try {
                        d = v(d)
                    } catch (e) {
                        y.call(this, e);
                        break
                    }
                }
                try {
                    c = Pt.call(this, d)
                } catch (e) {
                    return Promise.reject(e)
                }
                for (p = 0, l = f.length; p < l;) c = c.then(f[p++], f[p++]);
                return c
            }
        }, {
            key: "getUri", value: function (e) {
                return ke(nt((e = it(this.defaults, e)).baseURL, e.url, e.allowAbsoluteUrls), e.params, e.paramsSerializer)
            }
        }]), e
    }();
    he.forEach(["delete", "get", "head", "options"], (function (e) {
        Ft.prototype[e] = function (t, r) {
            return this.request(it(r || {}, {method: e, url: t, data: (r || {}).data}))
        }
    })), he.forEach(["post", "put", "patch"], (function (e) {
        function t(t) {
            return function (r, n, o) {
                return this.request(it(o || {}, {
                    method: e,
                    headers: t ? {"Content-Type": "multipart/form-data"} : {},
                    url: r,
                    data: n
                }))
            }
        }

        Ft.prototype[e] = t(), Ft.prototype[e + "Form"] = t(!0)
    }));
    var Bt = Ft, Dt = function () {
        function e(t) {
            if (d(this, e), "function" != typeof t) throw new TypeError("executor must be a function.");
            var r;
            this.promise = new Promise((function (e) {
                r = e
            }));
            var n = this;
            this.promise.then((function (e) {
                if (n._listeners) {
                    for (var t = n._listeners.length; t-- > 0;) n._listeners[t](e);
                    n._listeners = null
                }
            })), this.promise.then = function (e) {
                var t, r = new Promise((function (e) {
                    n.subscribe(e), t = e
                })).then(e);
                return r.cancel = function () {
                    n.unsubscribe(t)
                }, r
            }, t((function (e, t, o) {
                n.reason || (n.reason = new Ge(e, t, o), r(n.reason))
            }))
        }

        return y(e, [{
            key: "throwIfRequested", value: function () {
                if (this.reason) throw this.reason
            }
        }, {
            key: "subscribe", value: function (e) {
                this.reason ? e(this.reason) : this._listeners ? this._listeners.push(e) : this._listeners = [e]
            }
        }, {
            key: "unsubscribe", value: function (e) {
                if (this._listeners) {
                    var t = this._listeners.indexOf(e);
                    -1 !== t && this._listeners.splice(t, 1)
                }
            }
        }, {
            key: "toAbortSignal", value: function () {
                var e = this, t = new AbortController, r = function (e) {
                    t.abort(e)
                };
                return this.subscribe(r), t.signal.unsubscribe = function () {
                    return e.unsubscribe(r)
                }, t.signal
            }
        }], [{
            key: "source", value: function () {
                var t;
                return {
                    token: new e((function (e) {
                        t = e
                    })), cancel: t
                }
            }
        }]), e
    }(), qt = Dt;
    var It = {
        Continue: 100,
        SwitchingProtocols: 101,
        Processing: 102,
        EarlyHints: 103,
        Ok: 200,
        Created: 201,
        Accepted: 202,
        NonAuthoritativeInformation: 203,
        NoContent: 204,
        ResetContent: 205,
        PartialContent: 206,
        MultiStatus: 207,
        AlreadyReported: 208,
        ImUsed: 226,
        MultipleChoices: 300,
        MovedPermanently: 301,
        Found: 302,
        SeeOther: 303,
        NotModified: 304,
        UseProxy: 305,
        Unused: 306,
        TemporaryRedirect: 307,
        PermanentRedirect: 308,
        BadRequest: 400,
        Unauthorized: 401,
        PaymentRequired: 402,
        Forbidden: 403,
        NotFound: 404,
        MethodNotAllowed: 405,
        NotAcceptable: 406,
        ProxyAuthenticationRequired: 407,
        RequestTimeout: 408,
        Conflict: 409,
        Gone: 410,
        LengthRequired: 411,
        PreconditionFailed: 412,
        PayloadTooLarge: 413,
        UriTooLong: 414,
        UnsupportedMediaType: 415,
        RangeNotSatisfiable: 416,
        ExpectationFailed: 417,
        ImATeapot: 418,
        MisdirectedRequest: 421,
        UnprocessableEntity: 422,
        Locked: 423,
        FailedDependency: 424,
        TooEarly: 425,
        UpgradeRequired: 426,
        PreconditionRequired: 428,
        TooManyRequests: 429,
        RequestHeaderFieldsTooLarge: 431,
        UnavailableForLegalReasons: 451,
        InternalServerError: 500,
        NotImplemented: 501,
        BadGateway: 502,
        ServiceUnavailable: 503,
        GatewayTimeout: 504,
        HttpVersionNotSupported: 505,
        VariantAlsoNegotiates: 506,
        InsufficientStorage: 507,
        LoopDetected: 508,
        NotExtended: 510,
        NetworkAuthenticationRequired: 511
    };
    Object.entries(It).forEach((function (e) {
        var t = b(e, 2), r = t[0], n = t[1];
        It[n] = r
    }));
    var Mt = It;
    var zt = function e(t) {
        var r = new Bt(t), n = R(Bt.prototype.request, r);
        return he.extend(n, Bt.prototype, r, {allOwnKeys: !0}), he.extend(n, r, null, {allOwnKeys: !0}), n.create = function (r) {
            return e(it(t, r))
        }, n
    }(De);
    return zt.Axios = Bt, zt.CanceledError = Ge, zt.CancelToken = qt, zt.isCancel = Ve, zt.VERSION = Lt, zt.toFormData = Ee, zt.AxiosError = de, zt.Cancel = zt.CanceledError, zt.all = function (e) {
        return Promise.all(e)
    }, zt.spread = function (e) {
        return function (t) {
            return e.apply(null, t)
        }
    }, zt.isAxiosError = function (e) {
        return he.isObject(e) && !0 === e.isAxiosError
    }, zt.mergeConfig = it, zt.AxiosHeaders = We, zt.formToJSON = function (e) {
        return Fe(he.isHTMLForm(e) ? new FormData(e) : e)
    }, zt.getAdapter = jt, zt.HttpStatusCode = Mt, zt.default = zt, zt
}));
//# sourceMappingURL=axios.min.js.map