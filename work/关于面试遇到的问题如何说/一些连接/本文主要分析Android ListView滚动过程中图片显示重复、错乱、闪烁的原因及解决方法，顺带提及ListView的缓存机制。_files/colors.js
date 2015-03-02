// Color library for Associates Central

// RGB object
function RGB(red, green, blue) {
    // These are integers between 0 and 255 inclusive.
    this.r = red;
    this.g = green;
    this.b = blue;

    // Accessor methods for the RGB components.
    this.getR = function() { return this.r; };
    this.getG = function() { return this.g; };
    this.getB = function() { return this.b; };

    // Defines and returns the 6-digit hex representation
    // of this RGB color.
    this.getHex = function() {
        if (!this.hex) {
            var map = '0123456789ABCDEF';
            this.hex = '' +
                       map.substr(Math.floor(this.r / 16), 1) +
                       map.substr((this.r % 16), 1) +
                       map.substr(Math.floor(this.g / 16), 1) +
                       map.substr((this.g % 16), 1) +
                       map.substr(Math.floor(this.b / 16), 1) +
                       map.substr((this.b % 16), 1);
        }
        return this.hex;
    };

    // Returns the equivalent HSV object.
    this.toHSV = function() {
        // Normalize RGB values for color space transform.
        var r = this.r / 255;
        var g = this.g / 255;
        var b = this.b / 255;

        // Helper values for color space transform.
        var max = Math.max(Math.max(r,g), b);
        var min = Math.min(Math.max(r,g), b);
        var delta = max - min;

        // Color space transform.
        var h = 0;
        var s = 0;
        var v = max;

        if (max > 0) {
            s = delta / max;
        }

        if (delta > 0) {
            if ((max == r) && (max != g)) {
                h += (g - b) / delta;
            }
            if ((max == g) && (max != b)) {
                h += (2 + (b - r) / delta);
            }
            if ((max == b) && (max != r)) {
                h += (4 + (r - g) / delta);
            }
            h = h * 60;
            if (h < 0) {
                h += 360;
            }
        }

        return new HSV(h, s, v);
    };

    // for debugging
    this.dump = function() {
        alert('Red: ' + this.r + "\n" +
              'Green: ' + this.g + "\n" +
              'Blue: ' + this.b + "\n" +
              'Hex: ' + this.getHex()
                );
    };
}

// HSV object
function HSV(hue, saturation, value) {
    // These are real numbers between 0 and 1 inclusive.
    this.h = hue;
    this.s = saturation;
    this.v = value;

    // Accessor methods for the HSV components.
    this.getH = function() {
        return this.h;
    };
    this.getS = function() {
        return this.s;
    };
    this.getV = function() {
        return this.v;
    };

    // Returns the equivalent RGB object.
    this.toRGB = function() {
        var sat_r;
        var sat_g;
        var sat_b;

        // Color space transform
        if (this.h < 120) {
            sat_r = (120 - this.h) / 60;
            sat_g = this.h / 60;
            sat_b = 0;
        } else if (this.h < 240) {
            sat_r = 0;
            sat_g = (240 - this.h) / 60;
            sat_b = (this.h - 120) / 60;
        } else {
            sat_r = (this.h - 240) / 60;
            sat_g = 0;
            sat_b = (360 - this.h) / 60;
        }

        if (sat_r > 1) {
            sat_r = 1;
        }
        if (sat_g > 1) {
            sat_g = 1;
        }
        if (sat_b > 1) {
            sat_b = 1;
        }

        return new RGB(
                Math.round((1 - this.s + this.s * sat_r) * this.v * 255),
                Math.round((1 - this.s + this.s * sat_g) * this.v * 255),
                Math.round((1 - this.s + this.s * sat_b) * this.v * 255)
                );
    };

    // for debugging
    this.dump = function() {
        alert('Hue: ' + this.h + "\n" +
              'Saturation: ' + this.s + "\n" +
              'Value: ' + this.v + "\n"
                );
    };
}

/* Container object that holds an RGB object and an HSV object.
   This is the catch-all type that gives you everything
   you will need up front.  With this object, you can create
   a color from RGB values, HSV values, or a hex string.

   Upon object creation, you are not guaranteed that any of the
   private properties exist yet (they are created as needed).
   Therefore, you must use accessor methods to get each property
   of this object.
*/
function Color(type, a, b, c) {
    if (type == 'rgb') {
        this.rgb = new RGB(a, b, c);
        this.r = a;
        this.g = b;
        this.b = c;
    } else if (type == 'hsv') {
        this.hsv = new HSV(a, b, c);
        this.h = a;
        this.s = b;
        this.v = c;
    }else if (type.match(/[0-9a-f]{6}/i)) {
        // 'type' argument was a hex color value
        var map = '0123456789ABCDEF';
        type = type.toUpperCase();
        this.r = map.indexOf(type.substr(0, 1)) * 16 + map.indexOf(type.substr(1, 1));
        this.g = map.indexOf(type.substr(2, 1)) * 16 + map.indexOf(type.substr(3, 1));
        this.b = map.indexOf(type.substr(4, 1)) * 16 + map.indexOf(type.substr(5, 1));
        this.rgb = new RGB(this.r, this.g, this.b);
        this.hex = type;
    } else {
        // nothing to work with
        return null;
    }

    // Accessor methods for properties.
    // Requested properties are calculated when first requested if necessary,
    // then stored for later.
    this.getR = function() {
        if (!this.r) {
            if (!this.rgb) {
                this.rgb = this.hsv.toRGB();
            }
            this.r = this.rgb.getR();
        }
        return this.r;
    };

    this.getG = function() {
        if (!this.g) {
            if (!this.rgb) {
                this.rgb = this.hsv.toRGB();
            }
            this.g = this.rgb.getG();
        }
        return this.g;
    };

    this.getB = function() {
        if (!this.b) {
            if (!this.rgb) {
                this.rgb = this.hsv.toRGB();
            }
            this.b = this.rgb.getB();
        }
        return this.b;
    };

    this.getH = function() {
        if (!this.h) {
            if (!this.hsv) {
                this.hsv = this.rgb.toHSV();
            }
            this.h = this.hsv.getH();
        }
        return this.h;
    };

    this.getS = function() {
        if (!this.s) {
            if (!this.hsv) {
                this.hsv = this.rgb.toHSV();
            }
            this.s = this.hsv.getS();
        }
        return this.s;
    };

    this.getV = function() {
        if (!this.v) {
            if (!this.hsv) {
                this.hsv = this.rgb.toHSV();
            }
            this.v = this.hsv.getV();
        }
        return this.v;
    };

    this.getHex = function() {
        if (!this.hex) {
            if (!this.rgb) {
                this.rgb = this.hsv.toRGB();
            }
            this.hex = this.rgb.getHex();
        }
        return this.hex;
    };

    // Return a contrasting color of the same hue.
    this.getContrasting = function() {
        var newH = this.getH();
        var newS = this.getS();
        var newV = this.getV();

        if (newS == 0) {
            // Grayscale; don't change saturation
            newV = (newV + 0.5) % 1;
        } else {
            newS = (newS + 0.5) % 1;
            newV = (newV + 0.5) % 1;

            // Adjust color for better contrast if it's in the deep blue range.
            if ((newH > 200) && (newH < 275)) {
                if ((newS >= 0.5) && (newV >= 0.5)) {
                    // High saturation and value, decrease saturation
                    newS = newS / 2;
                } else if ((newS <= 0.5) && (newV <= 0.5)) {
                    // Low saturation and value, increase value
                    newV = newV + 0.5;
                }
            }
        }

        return new Color('hsv', newH, newS, newV);
    };

    this.getBlendingColor = function() {
        var newH = this.getH();
        var newV = this.getV();
        var newS = this.getS();

        var tweak = (newS > 0.8) ? 0.1 : 0;
        if( newV > 0.6 ) {
            newV = newV - 0.2 - tweak;
        } else if(newV < 0.4) {
            newV = newV + 0.2 + tweak;
        } else {
            newV = newV - 0.3;
        }

        if( newH > 200 && newH < 275 ) {
            if( newS > 0.8 ) {
                newS = newS - 0.4;
            }
        }
        
        return new Color('hsv', newH, newS, newV);
    };

    this.isDark = function() {
        var rval = false;
        var v = this.getV();

        if (v < 0.6) {
            // Low value...it's dark.
            rval = true;
        } else {
            var h = this.getH();
            var s = this.getS();

            // A strongly-saturated blue or purple is considered "dark".
            if ((h > 200) && (h < 275)) {
                if (s > 0.5) {
                    rval = true;
                }
            }
        }

        return rval;
    };

    // for debugging
    this.dump = function() {
        alert('Red: ' + this.getR() + "\n" +
              'Green: ' + this.getG() + "\n" +
              'Blue: ' + this.getB() + "\n" +
              'Hex: ' + this.getHex() + "\n" +
              'Hue: ' + this.getH() + "\n" +
              'Saturation: ' + this.getS() + "\n" +
              'Value: ' + this.getV()
                );
    };
}

