package me.himashusoni.thermalprinter.core

import androidx.annotation.IntRange
import kotlin.experimental.or

object PrinterCommands {

    /////////////////////////////////////// PRINT COMMANDS /////////////////////////////////////////
    /**
     * ### LF
     * Print and line feed
     *
     * Prints the data in the print buffer and feeds one line, based on the current line spacing.
     * When the print buffer is empty, LF feeds one line.
     *
     *
     * @return `ByteArray` with command
     *
     * */
    fun lineFeed(): ByteArray {
        return byteArrayOf(AsciiByte.LF)
    }

    /**
     * ### FF
     * Print and return to Standard mode (in Page mode)
     *
     * In Page mode, prints all the data in the print buffer collectively and switches from Page
     * mode to Standard mode.
     *
     * @return `ByteArray` with command
     * */
    fun returnToStandardMode(): ByteArray {
        return byteArrayOf(AsciiByte.FF)
    }

    /**
     * ### CR
     * Print and carriage return
     *
     * Executes one of the following operations:
     * ```
     * |-----------------------------------------------------------------------------------------|
     * | Print head alignment | When auto line feed is enabled | When auto line feed is disabled |
     * |-----------------------------------------------------------------------------------------|
     * | Horizontal alignment | Executes printing and one      | This command is ignored.        |
     * |                      | line feed as LF                |                                 |
     * |-----------------------------------------------------------------------------------------|
     * | Vertical alignment   | Executes printing and one      | In Standard mode, prints the    |
     * |                      | line feed as LF                | data in the print buffer and    |
     * |                      |                                | moves the print position to the |
     * |                      |                                | beginning of the print line.    |
     * |                      |                                | in Page mode, moves the print   |
     * |                      |                                | position to the beginning of    |
     * |                      |                                | the print line.                 |
     * |-----------------------------------------------------------------------------------------|
     * ```
     * **Horizontal alignment** is applied to Line thermal head or Shuttle head.
     *
     * **Vertical alignment** is applied to Serial dot head.
     *
     * @return `ByteArray` with command
     * */
    fun printAndCarriageReturn(): ByteArray {
        return byteArrayOf(AsciiByte.CR)
    }

    /**
     * ### ESC FF
     * Print data in Page mode
     *
     * In Page mode, prints the data in the print buffer collectively.
     *
     * @return `ByteArray` with command
     * */
    fun printDataInPageMode(): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.FF)
    }

    /**
     * ### ESC J n
     * Print and feed paper
     *
     * n = 0-255
     *
     * ESC J prints the data in the print buffer and feeds n dots.
     *
     * The command will not change the setting set by command ESC 2，ESC 3.
     *
     * @param dots n
     *
     * @return `ByteArray` with command
     * */
    fun printAndFeed(@IntRange(from = 0, to = 255) dots: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.UPPERCASE_J, dots.toByte())
    }

    /**
     * ### ESC K n
     * Print and reverse feed
     *
     * Prints the data in the print buffer and feeds the paper
     *
     * n × (vertical or horizontal motion unit) in the reverse direction.
     *
     * @param dots n
     *
     * @return `ByteArray` with command
     * */
    fun printAndReverseFeed(dots: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.UPPERCASE_K, dots.toByte())
    }

    /**
     * ### ESC d n
     * Print and feed n lines
     *
     * n = 0-255
     *
     * Prints the data in the print buffer and feeds n lines.
     *
     * @param lines n
     *
     * @return `ByteArray` with command
     * */
    fun printAndLineFeed(@IntRange(from = 0, to = 255) lines: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.LOWERCASE_D, lines.toByte())
    }

    /**
     * ### ESE e n
     * Print and reverse feed n lines
     *
     * Prints the data in the print buffer and feeds n lines in the reverse direction.
     *
     * @param lines n
     *
     * @return `ByteArray` with command
     * */
    fun printAndReverseLine(lines: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.LOWERCASE_E, lines.toByte())
    }

    /////////////////////////////////////// PRINT COMMANDS /////////////////////////////////////////


    /////////////////////////////////// LINE SPACING COMMANDS //////////////////////////////////////

    /**
     * ### ESC 2
     * Select default line spacing
     *
     * ESC 2 sets the line space to default value (30 dots)
     *
     * @return `ByteArray` with command
     * */
    fun setDefaultLineSpacing(): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.DIGIT_2)
    }

    /**
     * ### ESC 3 n
     * Set line spacing
     *
     * n = 0-255
     *
     * ESC 3 n sets the line spacing to n dots. The default value is 30
     *
     * @return `ByteArray` with command
     * */
    fun setLineSpacing(@IntRange(from = 0, to = 255) dots: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.DIGIT_3, dots.toByte())
    }

    /////////////////////////////////// LINE SPACING COMMANDS //////////////////////////////////////


    ///////////////////////////////////// CHARACTER COMMANDS ///////////////////////////////////////

    /**
     * ### CAN
     * Cancel print data in Page mode
     *
     * in Page mode, deletes all the print data in the current print area.
     *
     * @return `ByteArray` with command
     * */
    fun cancelPrint(): ByteArray {
        return byteArrayOf(AsciiByte.CAN)
    }

    /**
     * ### ESC SP n
     * Set right-side character spacing
     *
     * n = 0–255
     *
     * Sets the right-side character spacing to n × (horizontal or vertical motion unit).
     *
     * @param dots n
     *
     * @return `ByteArray` with command
     * */
    fun setRightSodeCharacterSpacing(@IntRange(from = 0, to = 255) dots: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.SPACE, dots.toByte())
    }

    /**
     * ### ESC ! n
     * Select print mode
     *
     * The default value is 0. This command is effective for all characters.
     *
     * * BIT0:
     * * BIT1:
     * * BIT2:
     * * BIT3: 1:Emphasized mode selected
     *       0:Emphasized mode not selected
     * * BIT4: 1:Double Height mode selected
     *       0:Double Height mode not selected
     * * BIT5: 1:Double Width mode selected
     *       0:Double Width mode not selected
     * * BIT6: 1:Deleteline mode selected
     *       0:Deleteline mode not selected
     * * BIT7: 1:Underline mode selected
     *       0:Underline mode not selected
     *
     *  @param bold to set font as bold, default is false
     *  @param largeHeight to set font height large, default is false
     *  @param largeWidth to set font width large, default is false
     *  @param deleteLine to set font as delete line, default is false
     *  @param underLine to set font as underlined, default is false
     *
     *  @return ByteArray with command
     * */
    fun setPrintMode(
        bold: Boolean = false,
        largeHeight: Boolean = false,
        largeWidth: Boolean = false,
        deleteLine: Boolean = false,
        underLine: Boolean = false
    ): ByteArray {
        var default: Byte = 0b00000000
        if (underLine) default = default or 0b10000000.toByte()
        if (deleteLine) default = default or 0b01000000.toByte()
        if (largeWidth) default = default or 0b00100000.toByte()
        if (largeHeight) default = default or 0b00010000.toByte()
        if (bold) default = default or 0b00001000.toByte()
        return byteArrayOf(AsciiByte.ESC, AsciiByte.EXCLAMATION_MARK, default)
    }

    /**
     * ### ESC - n
     * Turn underline mode on/off
     *
     * Turns underline mode on or off using n as follows:
     *
     * ```
     * |------------------------------------------------|
     * |   n   | Function                               |
     * |------------------------------------------------|
     * | 0, 48	| Turns off underline mode               |
     * | 1, 49	| Turns on underline mode (1-dot thick)  |
     * | 2, 50	| Turns on underline mode (2-dots thick) |
     * |------------------------------------------------|
     * ```
     *
     * @param n dots
     *
     * @return ByteArray with command
     * */
    fun setUnderlineMode(@IntRange(from = 0, to = 2) n: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.HYPHEN, n.toByte())
    }

    /**
     * ### ESC E n
     * Turn emphasized mode on/off
     *
     * n = 0 – 255
     *
     * Turns emphasized mode on or off.
     * * When the LSB of n is 0, emphasized mode is turned off.
     * * When the LSB of n is 1, emphasized mode is turned on.
     *
     * @param enable to enable emphasized mode
     *
     * @return `ByteArray` with command
     * */
    fun setEmphasizeMode(enable: Boolean): ByteArray {
        val mode: Byte = if (enable) 0b00000001 else 0b00000000
        return byteArrayOf(AsciiByte.ESC, AsciiByte.UPPERCASE_E, mode)
    }

    /**
     * ### ESC G n
     * Turn double-strike mode on/off
     *
     * n = 0 – 255
     *
     * Turns double-strike mode on or off.
     * * When the LSB of n is 0, double-strike mode is turned off.
     * * When the LSB of n is 1, double-strike mode is turned on.
     *
     * @param enable to enable double-strike mode
     *
     * @return `ByteArray` with command
     * */
    fun setDoubleStrikeMode(enable: Boolean): ByteArray {
        val mode: Byte = if (enable) 0b00000001 else 0b00000000
        return byteArrayOf(AsciiByte.ESC, AsciiByte.UPPERCASE_G, mode)
    }

    /**
     * ### ESC M n
     * Select character font
     *
     * n = different depending on printers
     *
     * Selects a character font, using n as follows:
     * ```
     * |------------------------|
     * |   n   |      Font      |
     * |------------------------|
     * | 0, 48 |    Font A      |
     * | 1, 49 |    Font B      |
     * | 2, 50 |    Font C      |
     * | 3, 51 |    Font D      |
     * | 4, 52 |    Font E      |
     * |  97   | Special font A |
     * |  98   | Special font B |
     * --------------------------
     * ```
     * @param font selected font
     *
     * @return `ByteArray` with command
     * */
    fun setCharacterFont(font: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.UPPERCASE_M, font.toByte())
    }

    /**
     * ESC R n
     * Select international character set
     *
     * Selects an international character set n as follows:
     * ```
     * ----------------------------
     * |  n  |      Country       |
     * ----------------------------
     * |  0  |       U.S.A.       |
     * |  1  |      France        |
     * |  2  |      Germany       |
     * |  3  |       U.K.         |
     * |  4  |     Denmark I      |
     * |  5  |      Sweden        |
     * |  6  |       Italy        |
     * |  7  |      Spain I       |
     * |  8  |       Japan        |
     * |  9  |      Norway        |
     * | 10  |     Denmark II     |
     * | 11  |     Spain II       |
     * | 12  |   Latin America    |
     * | 13  |       Korea        |
     * | 14  | Slovenia / Croatia |
     * | 15  |      China         |
     * | 16  |     Vietnam        |
     * | 17  |      Arabia        |
     * | 66  | India (Devanagari) |
     * | 67  |  India (Bengali)   |
     * | 68  |   India (Tamil)    |
     * | 69  |   India (Telugu)   |
     * | 70  |  India (Assamese)  |
     * | 71  |   India (Oriya)    |
     * | 72  |   India (Kannada)  |
     * | 73  |  India (Malayalam) |
     * | 74  |  India (Gujarati)  |
     * | 75  |  India (Punjabi)   |
     * | 82  |   India (Marathi)  |
     * ----------------------------
     * ```
     * @param n selected international character set
     *
     * @return `ByteArray` with command
     * */
    fun setInternationalCharacterSet(n: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.UPPERCASE_R, n.toByte())
    }

    /**
     * ESC V n
     * Turn 90° clockwise rotation mode
     *
     * n = 0–2, 48–50, default 0
     *In Standard mode, turns 90° clockwise rotation mode on or off for characters, using n as follows:
     * ```
     * ----------------------------------------------------------------------------
     * |   n   |             Function                                             |
     * ----------------------------------------------------------------------------
     * | 0, 48 | Turns off 90° clockwise rotation mode                            |
     * | 1, 49 | Turns on 90° clockwise rotation mode (1-dot character spacing)   |
     * | 2, 50 | Turns on 90° clockwise rotation mode (1.5-dot character spacing) |
     * ----------------------------------------------------------------------------
     * ```
     * @param n turn on or off rotation
     *
     * @return `ByteArray` with command
     * */
    fun set90degreeClockwiseRotationMode(n: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.UPPERCASE_V, n.toByte())
    }

    /**
     * ESC r n
     * Select print color
     *
     * n = 0, 1, 48, 49, default 0
     * Selects a print color, using n as follows:
     * ```
     * ----------------------|
     * |   n   | Print color |
     * ----------------------|
     * | 0, 48 |   Black     |
     * | 1, 49 |    Red      |
     * ----------------------|
     * ```
     * @param n color
     *
     * @return `ByteArray` with command
     * */
    fun setPrintColor(n: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.LOWERCASE_R, n.toByte())
    }

    /**
     * ESC t n
     * Select character code table
     *
     * Selects a page n from the character code table as follows:
     * ```
     * -----------------------------------------------------
     * |  n  | Character code table                        |
     * |---------------------------------------------------|
     * |  0  | Page 0 [PC437: USA, Standard Europe]        |
     * |  1  | Page 1 [Katakana]                           |
     * |  2  | Page 2 [PC850: Multilingual]                |
     * |  3  | Page 3 [PC860: Portuguese]                  |
     * |  4  | Page 4 [PC863: Canadian-French]             |
     * |  5  | Page 5 [PC865: Nordic]                      |
     * |  6  | Page 6 [Hiragana]                           |
     * |  7  | Page 7 [One-pass printing Kanji characters] |
     * |  8  | Page 8 [One-pass printing Kanji characters] |
     * | 11  | Page 11 [PC851: Greek]                      |
     * | 12  | Page 12 [PC853: Turkish]                    |
     * | 13  | Page 13 [PC857: Turkish]                    |
     * | 14  | Page 14 [PC737: Greek]                      |
     * | 15  | Page 15 [ISO8859-7: Greek]                  |
     * | 16  | Page 16 [WPC1252]                           |
     * | 17  | Page 17 [PC866: Cyrillic #2]                |
     * | 18  | Page 18 [PC852: Latin 2]                    |
     * | 19  | Page 19 [PC858: Euro]                       |
     * | 20  | Page 20 [Thai Character Code 42]            |
     * | 21  | Page 21 [Thai Character Code 11]            |
     * | 22  | Page 22 [Thai Character Code 13]            |
     * | 23  | Page 23 [Thai Character Code 14]            |
     * | 24  | Page 24 [Thai Character Code 16]            |
     * | 25  | Page 25 [Thai Character Code 17]            |
     * | 26  | Page 26 [Thai Character Code 18]            |
     * | 30  | Page 30 [TCVN-3: Vietnamese]                |
     * | 31  | Page 31 [TCVN-3: Vietnamese]                |
     * | 32  | Page 32 [PC720: Arabic]                     |
     * | 33  | Page 33 [WPC775: Baltic Rim]                |
     * | 34  | Page 34 [PC855: Cyrillic]                   |
     * | 35  | Page 35 [PC861: Icelandic]                  |
     * | 36  | Page 36 [PC862: Hebrew]                     |
     * | 37  | Page 37 [PC864: Arabic]                     |
     * | 38  | Page 38 [PC869: Greek]                      |
     * | 39  | Page 39 [ISO8859-2: Latin 2]                |
     * | 40  | Page 40 [ISO8859-15: Latin 9]               |
     * | 41  | Page 41 [PC1098: Farsi]                     |
     * | 42  | Page 42 [PC1118: Lithuanian]                |
     * | 43  | Page 43 [PC1119: Lithuanian]                |
     * | 44  | Page 44 [PC1125: Ukrainian]                 |
     * | 45  | Page 45 [WPC1250: Latin 2]                  |
     * | 46  | Page 46 [WPC1251: Cyrillic]                 |
     * | 47  | Page 47 [WPC1253: Greek]                    |
     * | 48  | Page 48 [WPC1254: Turkish]                  |
     * | 49  | Page 49 [WPC1255: Hebrew]                   |
     * | 50  | Page 50 [WPC1256: Arabic]                   |
     * | 51  | Page 51 [WPC1257: Baltic Rim]               |
     * | 52  | Page 52 [WPC1258: Vietnamese]               |
     * | 53  | Page 53 [KZ-1048: Kazakhstan]               |
     * | 66  | Page 66 [Devanagari]                        |
     * | 67  | Page 67 [Bengali]                           |
     * | 68  | Page 68 [Tamil]                             |
     * | 69  | Page 69 [Telugu]                            |
     * | 70  | Page 70 [Assamese]                          |
     * | 71  | Page 71 [Oriya]                             |
     * | 72  | Page 72 [Kannada]                           |
     * | 73  | Page 73 [Malayalam]                         |
     * | 74  | Page 74 [Gujarati]                          |
     * | 75  | Page 75 [Punjabi]                           |
     * | 82  | Page 82 [Marathi]                           |
     * | 254 | Page 254                                    |
     * | 255 | Page 255                                    |
     * -----------------------------------------------------
     * ```
     *
     * @param n character code
     *
     * @return `ByteArray` with command
     * */
    fun setCharacterCode(n: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.LOWERCASE_T, n.toByte())
    }

    /**
     * ESC { n
     * Turn upside down printing mode on/off
     *
     * n = 0 – 255, default 0
     *
     * In Standard mode, turns upside-down print mode on or off.
     * * When the LSB of n is 0, upside-down print mode is turned off.
     * * When the LSB of n is 1, upside-down print mode is turned on.
     *
     * @param enable true to enable upside down mode, false to disable
     *
     * @return `ByteArray` with command
     */
    fun setUpsideDownPrintingMode(enable: Boolean): ByteArray {
        val mode: Byte = if (enable) 0b00000001 else 0b00000000
        return byteArrayOf(AsciiByte.ESC, AsciiByte.LEFT_BRACE, mode)
    }

    /**
     * GS ! n
     * Select character size
     *
     * n = 0xxx0xxxb (n = 0 – 7, 16 – 23, 32 – 39, 48 – 55, 64 – 71, 80 – 87, 96 – 103, 112 – 119)
     * (Enlargement in vertical direction: 1–8, Enlargement in horizontal direction: 1–8), default 0
     *
     * Selects character size (height magnification and width magnification).
     * ```
     * -----------------------------------------------------------------------------------
     * | (n) Bit |             Function             |       Hex      |     Decimal       |
     * -----------------------------------------------------------------------------------
     * |   0–2   | Selects the height magnification | See table [Height magnification].  |
     * |    3    | (Reserved)                       |      00        |        0          |
     * |   4–6   | Selects the width magnification  | See table [Width magnification].   |
     * |    7    | (Reserved)                       |      00        |        0          |
     * -----------------------------------------------------------------------------------
     * ```
     * Character width selection
     * ```
     * ---------------------------------------------------------
     * |        Bit      |  Hex | Decimal |        Width       |
     * |  6  |  5  |  4  |      |         |                    |
     * --------------------------------------------------------|
     * |  0  |  0  |  0  |  00  |    0    |    x 1 (normal)    |
     * |  0  |  0  |  1  |  10  |    16   | x 2 (double-width) |
     * |  0  |  1  |  0  |  20  |    32   |        x 3         |
     * |  0  |  1  |  1  |  30  |    48   |        x 4         |
     * |  1  |  0  |  0  |  40  |    64   |        x 5         |
     * |  1  |  0  |  1  |  50  |    80   |        x 6         |
     * |  1  |  1  |  0  |  60  |    96   |        x 7         |
     * |  1  |  1  |  1  |  70  |   112   |        x 8         |
     * ---------------------------------------------------------
     * ```
     * Character height selection
     * ```
     * ----------------------------------------------------------
     * |        Bit      |  Hex | Decimal |        Height       |
     * |  2  |  1  |  0  |      |         |                     |
     * ----------------------------------------------------------
     * |  0  |  0  |  0  |  00  |    0    |    x 1 (normal)     |
     * |  0  |  0  |  1  |  01  |    1    | x 2 (double-height) |
     * |  0  |  1  |  0  |  02  |    2    |         x 3         |
     * |  0  |  1  |  1  |  03  |    3    |         x 4         |
     * |  1  |  0  |  0  |  04  |    4    |         x 5         |
     * |  1  |  0  |  1  |  05  |    5    |         x 6         |
     * |  1  |  1  |  0  |  06  |    6    |         x 7         |
     * |  1  |  1  |  1  |  07  |    7    |         x 8         |
     * ----------------------------------------------------------
     * ```
     * @param width size of width from 1 to 8
     * @param height size of height from 1 to 8
     *
     * @return `ByteArray` with command
     * */
    fun setCharacterSize(
        @IntRange(from = 1, to = 8) width: Int,
        @IntRange(from = 1, to = 8) height: Int
    ): ByteArray {
        val widthBits654 = ((width - 1) shl 4)
        val heightBits210 = (height - 1)
        val size = widthBits654 or heightBits210
        return byteArrayOf(AsciiByte.GS, AsciiByte.EXCLAMATION_MARK, size.toByte())
    }

    /**
     * GS B n
     * Turn white/black reverse printing mode on/off
     *
     * n = 0 – 255, default 0
     * Turns white/black reverse print mode on or off.
     * * When the LSB of n is 0, white/black reverse print mode is turned off.
     * * When the LSB of n is 1, white/black reverse print mode is turned on.
     *
     * @param reverse true to enable color reverse, false to disable
     *
     * @return `ByteArray` with command
     * */
    fun setColorReverse(reverse: Boolean): ByteArray {
        val mode = if (reverse) 0b00000001 else 0b00000000
        return byteArrayOf(AsciiByte.GS, AsciiByte.UPPERCASE_B, mode.toByte())
    }


    /**
     * GS b n
     * Turn smoothing mode on/off
     *
     * n = 0 – 255, default 0
     * Turns smoothing mode on or off.
     * * When the LSB of n is 0, smoothing mode is turned off.
     * * When the LSB of n is 1, smoothing mode is turned on.
     *
     * @param enable true to enable smoothing mode, false to disable
     *
     * @return `ByteArray` with command
     * */
    fun setSmoothingMode(enable: Boolean): ByteArray {
        val mode = if (enable) 0b00000001 else 0b00000000
        return byteArrayOf(AsciiByte.GS, AsciiByte.LOWERCASE_B, mode.toByte())
    }


    /**
     * ESC % n
     * Enable Disable User-defined Characters
     *
     * n = 0 – 255, default 0
     * Selects or cancels the user-defined character set.
     * * When the LSB of n is 0, the user-defined character set is canceled.
     * * When the LSB of n is 1, the user-defined character set is selected.
     *
     * @param enable true to enable user defined characters, false to disable
     *
     * @return `ByteArray` with command
     * */
    fun enableUserDefinedCharacters(enable: Boolean): ByteArray {
        val mode = if (enable) 0b00000001 else 0b00000000
        return byteArrayOf(AsciiByte.ESC, AsciiByte.PERCENT_SIGN, mode.toByte())
    }

    /**
     * ESC & s n m w d1 d2..dx
     * Define User-defined characters
     *
     * The command is used to define user-defined character.
     * Max 64 user chars can be defined.
     *
     * s = 3, 32 ≤ n ≤ m < 127
     * s: Character height bytes, = 3 (24dots)
     * n: User-defined character starting code
     * m: User-defined character ending code
     * w: Character width 0～12 (s = 3)
     * dx: data, x=s*w
     *
     * s=3
     * ```
     * --------------------------------------
     * | d1 | d4 | d7 | | | | | | | | |     |
     * --------------------------------------
     * | d2 | d5 | d8 | | | | | | | | |     |
     * --------------------------------------
     * | d3 | d6 | d9 | | | | | | | | | d36 |
     * --------------------------------------
     *```
     *
     * dx format:
     * ```
     * |------|------|
     * |      | BIT7 |
     * |      |------|
     * |      | BIT6 |
     * |      |------|
     * |      | BIT5 |
     * |      |------|
     * |      | BIT4 |
     * |  dx  |------|
     * |      | BIT3 |
     * |      |------|
     * |      | BIT2 |
     * |      |------|
     * |      | BIT1 |
     * |      |------|
     * |      | BIT0 |
     * |------|------|
     * ```
     *
     * @return `ByteArray` with command
     * */
    fun setUserDefinedCharacterData(s: Int, n: Int, m: Int, w: Int, data: ByteArray): ByteArray {
        return byteArrayOf(
            AsciiByte.ESC, AsciiByte.AMPERSAND,
            s.toByte(), n.toByte(), m.toByte(), w.toByte(),
            *data
        )
    }

    /**
     * ESC ? n
     * Disable user-defined character
     *
     * n = 32 – 126
     * Deletes the user-defined character pattern specified by character code n.
     *
     * @param n character code to disable
     *
     * @return `ByteArray` with command
     * */
    fun disableUserDefinedCharacter(@IntRange(from = 32, to = 126) n: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.QUESTION_MARK, n.toByte())
    }

    ///////////////////////////////////// CHARACTER COMMANDS ///////////////////////////////////////


    ///////////////////////////////////// PRINT POSITION COMMANDS ///////////////////////////////////////

    /**
     * HT
     * Horizontal tab
     *
     * Moves the print position to the next horizontal tab position.
     *
     * @return `ByteArray` with command
     */
    fun horizontalTab(): ByteArray {
        return byteArrayOf(AsciiByte.HT)
    }

    /**
     * ESC $ nL nH
     * Set absolute print position
     *
     * (nL + nH × 256) = 0 – 65535
     * Moves the print position to (nL + nH × 256) × (horizontal or vertical motion unit) from
     * the left edge of the print area.
     *
     * @param nL
     * @param nH
     *
     * @return `ByteArray` with command
     * */
    fun setPrintPosition(nL: Int, nH: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.DOLLAR_SIGN, nL.toByte(), nH.toByte())
    }

    /**
     * ESC D n1 ... nk NUL
     * Set horizontal tab positions
     *
     * n = 1 – 255
     * k = 0 – 32
     *
     * default
     * n = 8, 16, 24, 32, ..., 232, 240, 248 (Every eight characters for the default font set)
     *
     * Sets horizontal tab positions.
     * * n specifies the number of digits from the setting position to the left edge of the print area.
     * * k indicates the number of horizontal tab positions to be set.
     *
     * @param positions tab positions to set
     *
     * @return `ByteArray` with command
     * */
    fun setHorizontalTabPositions(vararg positions: Int): ByteArray {
        return byteArrayOf(
            AsciiByte.ESC,
            AsciiByte.UPPERCASE_D,
            *positions.map { it.toByte() }.toByteArray(),
            positions.size.toByte()
        )
    }

    /**
     * ESC T n
     * Select print direction in Page mode
     *
     * n = 0 – 3, 48 – 51, default 0
     * in Page mode, selects the print direction and starting position using n as follows:
     * ```
     * -------------------------------------------------------
     * |   n   | Print direction |     Starting position     |
     * |-----------------------------------------------------|
     * | 0, 48 |  Left to right  |  Upper left (A in figure) |
     * | 1, 49 |  Bottom to top  |  Lower left (B in figure) |
     * | 2, 50 |  Right to left  | Lower right (C in figure) |
     * | 3, 51 |  Top to bottom  | Upper right (D in figure) |
     * -------------------------------------------------------
     *
     * --------------------------
     * | A --->               D |
     * |                      | |
     * |                      | |
     * | ^                    ˅ |
     * | |                      |
     * | |                      |
     * | B               <--- C |
     * --------------------------
     *         FIGURE
     * ```
     * @param n direction
     *
     * @return `ByteArray` with command
     * */
    fun setPrintDirection(n: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.UPPERCASE_T, n.toByte())
    }

    /**
     * ESC W xL xH yL yH dxL dxH dyL dyH
     * Select print area in page mode
     *
     * (xL + xH × 256) = 0 – 65535
     * (yL + yH × 256) = 0 – 65535
     * (dxL + dxH × 256) = 1 – 65535
     * (dyL + dyH × 256) = 1 – 65535
     *
     * default:
     * Horizontal logical origin and vertical logical origin = 0
     * (xL + xH × 256) = 0
     * (yL + yH × 256) = 0
     * Print area width (dxL + dxH × 256) and print area height (dyL + dyH × 256) = entire printable area:
     * different depending on the printers
     *
     * in Page mode, sets the size and the logical origin of the print area as follows:
     * * Horizontal logical origin = (xL + xH × 256) × (horizontal motion unit) from absolute origin.
     * * Vertical logical origin = (yL + yH × 256) × (vertical motion unit) from absolute origin.
     * * Print area width = (dxL + dxH × 256) × (horizontal motion unit)
     * * Print area height = (dyL + dyH × 256) × (vertical motion unit)
     *
     * @return `ByteArray` with command
     */
    fun setPrintArea(
        xL: Int,
        xH: Int,
        yL: Int,
        yH: Int,
        dxL: Int,
        dxH: Int,
        dyL: Int,
        dyH: Int
    ): ByteArray {
        return byteArrayOf(
            AsciiByte.ESC,
            AsciiByte.UPPERCASE_W,
            xL.toByte(),
            xH.toByte(),
            yL.toByte(),
            yH.toByte(),
            dxL.toByte(),
            dxH.toByte(),
            dyL.toByte(),
            dyH.toByte()
        )
    }

    /**
     * ESC \ nL nH
     *
     * */

    ///////////////////////////////////// PRINT POSITION COMMANDS ///////////////////////////////////////


    /*
    * ESC a n
    * Set align mode
    *
    * Default is 0
    * 0 <= n <= 2 or 48 <= n <= 50
    * Align left:   n=0,48
    * Align middle: n=1,49
    * Align right:  n-2,50
    * */
    fun setAlignMode(alignMode: AlignMode): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.LOWERCASE_A, alignMode.mode)
    }

    enum class AlignMode(val mode: Byte) {
        LEFT(0x0), MIDDLE(0x1), RIGHT(0x2),
        LEFT_A(0x48), MIDDLE_A(0x49), RIGHT_A(0x50)
    }

    /*
    * ESC B n
    * Set left blank char nums
    *
    * Default is 0
    * 0 <= n <= 47
    * */
    fun setLeftBlank(@IntRange(from = 0, to = 47) charNum: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.UPPERCASE_B, charNum.toByte())
    }


    /*
    * ESC SO
    * Select Double Width mode
    *
    * Select Double Width mode
    * To turn double width off, use LF or DC4 command
    * */
    fun enableDoubleWidthMode(): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.SO)
    }

    /*
    * ESC DC4
    * Disable Double Width mode
    *
    * Disable Double width mode
    * */
    fun disableDoubleWidthMode(): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.DC4)
    }

    /*
    * ESC * m nL nH d1 d2..dk
    * Select bit-image mode
    *
    * Attention: The command may clear the user defined char.
    * This command selects a but image mode using m for the number
    * of dots specified by (nL+nH*256).
    *
    *
    * m = 0, 1, 32, 33
    * nL = 0 - 255
    * nH = 0 - 3
    * dx = 0 - 255
    * k = nL + 256 * nH (m = 0, 1)
    * k = (nL + 256 * nH) * 3 (m = 32, 33)
    * The modes selected by m are as follows:
    * 0:  8 dots single density, 102 dpi
    * 1:  8 dots double density, 203 dpi
    * 31: 24 dots single density, 102 dpi
    * 32: 24 dots single density, 203 dpi
    *
    * The bit image format is same as user defined character.
    * */
    fun setBitImageMode(m: Int, nL: Int, nH: Int, data: ByteArray): ByteArray {
        return byteArrayOf(
            AsciiByte.ESC, AsciiByte.ASTERISK, m.toByte(),
            nL.toByte(), nH.toByte(), *data
        )
    }

    /*
    * GS / n
    * Print downloaded bit image
    *
    * This command prints a downloaded bit image using the mode
    * specified by n as specified ion the chart. In standard mode,
    * this command is effective only when there is data in the
    * print buffer. This command is ignored if a downloaded but has
    * not been defined.
    *
    * n = 0 - 3, 48 - 51: Specify bit image mode
    *
    * ---------------------------------------------------------
    * |   n   | Pattern Mode  | Vertical DPI | Horizontal DPI |
    * |-------------------------------------------------------|
    * | 0, 48 |     Normal    |    203 dpi   |     203 dpi    |
    * | 1, 49 | Double width  |    203 dpi   |     101 dpi    |
    * | 2, 50 | Double Height |    101 dpi   |     203 dpi    |
    * | 3, 51 |   Quadruple   |    101 dpi   |     101 dpi    |
    * --------|------------------------------------------------
    * */
    fun printDownloadedBitImage(@IntRange(from = 0, to = 3) n: Int): ByteArray {
        return byteArrayOf(AsciiByte.GS, AsciiByte.SLASH, n.toByte())
    }

    /*
    * GS * x y d1..dk
    * Define downloaded bit image
    *
    * This command defines a downloaded bit image by using x*8 dots in the
    * horizontal direction and y*8 dots in the vertical direction. Once a
    * downloaded bit image has been define, it is available until
    *  > Another definition is made
    *  > ESC & or ESC @ is executed
    *  > The power is turned off
    *  > The printer is reset
    *
    *  x = 1~48 (width)
    *  y = 1~255 (height)
    *  x × y < 1200
    *  k = x × y × 8
    * */
    fun defineDownloadedBitImage(x: Int, y: Int, data: ByteArray): ByteArray {
        return byteArrayOf(
            AsciiByte.GS, AsciiByte.ASTERISK,
            x.toByte(), y.toByte(), *data
        )
    }

    /*
    * ESC c 5 n
    * Enable/Disable the panel key
    *
    * This command has no effection.
    * n=1, Disable the panel key
    * n=0, Enable the panel key (Default)
    * */
    fun disablePanelKey(disable: Boolean): ByteArray {
        val mode = if (disable) 1 else 0
        return byteArrayOf(
            AsciiByte.ESC, AsciiByte.LOWERCASE_C,
            AsciiByte.DIGIT_5, mode.toByte()
        )
    }

    /*
    * ESC @
    * Initialize the printer
    *
    * Initializes the printer.
    * > The print buffer is cleared.
    * > Reset the param to default value.
    * > Return to standard mode
    * > Delete user-defined characters.
    * */
    fun initPrinter(): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.COMMERCIAL_AT)
    }

    /*
    * ESC v
    * Transmit paper sensor status
    *
    * Transmits the status of paper sensor as 1 byte of data.
    * The status byte definition:
    * --------------------------------------------
    * | Bit |          Function          | Value |
    * |-----|----------------------------|-------|
    * |  0  |         NO PRINTER         |       |
    * |  1  |                            |       |
    * |  2  |          NO PAPER          |   1   |
    * |  3  |        POWER ERROR         |   1   |
    * |  4  |             0              |   0   |
    * |  5  |                            |       |
    * |  6  |  PRINTER TEMPERATURE OVER  |   1   |
    * |  7  |                            |       |
    * --------------------------------------------
    * */
    fun transmitPaperSensorStatus(): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.LOWERCASE_V)
    }

    /*
    * GS a n
    * Enable/Disable Automatic Status Back (ASB)
    *
    * n definition as follows:
    * ----------------------------------------------------
    * |     |                         |        Value     |
    * | Bit |        Function         |------------------|
    * |     |                         |    0    |    1   |
    * |-----|-------------------------|---------|--------|
    * |  0  |            0            |         |        |
    * |  1  |                         |         |        |
    * |  2  |    Disable/Enable ASB   | Disable | Enable |
    * | 3-4 |                         |         |        |
    * |  5  |  Disable/Enable RTS as  | Disable | Enable |
    * |     |       flow control      |         |        |
    * | 6-7 |                         |         |        |
    * ----------------------------------------------------
    * When ASB is enabled, the printer will send the changed status
    * to PC automatically.
    * */
    fun enableASB(enableAsb: Boolean, enableRts: Boolean): ByteArray {
        val valueString =
            "00" + (if (enableAsb) "1" else "0") + "00" + (if (enableRts) "1" else "0") + "00"
        val value = valueString.toByte(2)
        return byteArrayOf(AsciiByte.GS, AsciiByte.LOWERCASE_A, value)
    }

    /*
    * ESC u n
    * Transmit peripheral devices status
    *
    * This command is not supported.
    * Return status bytes definition:
    * Bit0: Drawer status
    * Bit4: 0
    * Always return 0 back
    * */
    fun transmitPeripheralDeviceStatus(): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.LOWERCASE_U)
    }

    /*
    * GS H n
    * Select printing position of human readable characters
    *
    * 0 <= n <= 3
    * 48 <= n <= 51
    * This command selects the printing position for human readable
    * characters when printing a barcode. The default is n=0. Human
    * readable characters are printed using the font specified by GS fn.
    * Select the printing position as follows:
    * -------------------------------------------
    * |   n  | Printing Position                |
    * |------|----------------------------------|
    * | 0,48 | Not printed                      |
    * | 1,49 | Above the barcode                |
    * | 2,50 | Below the barcode                |
    * | 3,51 | Both above and below the barcode |
    * -------------------------------------------
    * */
    fun setBarcodePrintPosition(@IntRange(from = 48, to = 51) position: Int): ByteArray {
        return byteArrayOf(AsciiByte.GS, AsciiByte.UPPERCASE_H, position.toByte())
    }

    /*
    * GS h n
    * Sets barcode height
    *
    * This command selects the height of a barcode n specifies the number
    * of dots in the vertical direction. The default value is 50
    * 1 <= n <= 255
    * */
    fun setBarcodeHeight(@IntRange(from = 1, to = 255) dots: Int): ByteArray {
        return byteArrayOf(AsciiByte.GS, AsciiByte.LOWERCASE_H, dots.toByte())
    }

    /*
    * GS w n
    * Set barcode width
    *
    * This command selects the horizontal size of a barcode.
    * n = 2,3
    * The default value is 3
    * */
    fun setBarcodeWidth(@IntRange(from = 2, to = 3) width: Int): ByteArray {
        return byteArrayOf(AsciiByte.GS, AsciiByte.LOWERCASE_W, width.toByte())
    }


    /*
    * GS k m d1 d2..dk NUL
    *
    * m: barcode type
    * 0 <= m <= 10
    *
    * ----------------------------------------------------------
    * |       | Barcode |   No. Of   |         Remarks         |
    * |   m   | system  | Characters |                         |
    * |--------------------------------------------------------|
    * |  0,65 |  UPC-A  |    11,12   |          48-57          |
    * |  1,66 |  UPC-E  |    11,12   |          48-57          |
    * |  2,67 |  EAN13  |    12,13   |          48-57          |
    * |  3,68 |   EAN8  |     7,8    |          48-57          |
    * |  4,69 |  CODE39 |      >1    | 32,36,37,43,45-57,65-90 |
    * |  5,70 |   I25   |  >1 (even) |          48-57          |
    * |  6,71 | CODEBAR |      >1    |    36,43,45-58,65-68    |
    * |  7,72 | CODE93  |      >1    |          0-127          |
    * |  8,73 | CODE128 |      >1    |          0-127          |
    * |  9,74 | CODE11  |      >1    |          48-57          |
    * | 10,75 |   MSI   |      >1    |          48-57          |
    * ----------------------------------------------------------
    * */
    fun printBarcode(@IntRange(from = 0, to = 10) m: Int, data: ByteArray): ByteArray {
        return byteArrayOf(
            AsciiByte.GS, AsciiByte.LOWERCASE_K, m.toByte(),
            *data, AsciiByte.NUL
        )
    }

    /*
    * GS k m n d1 d2..dn
    *
    * m: barcode type
    * 65 <= m <= 75
    * n: barcode length
    *
    * ----------------------------------------------------------
    * |       | Barcode |   No. Of   |         Remarks         |
    * |   m   | system  | Characters |                         |
    * |--------------------------------------------------------|
    * |  0,65 |  UPC-A  |    11,12   |          48-57          |
    * |  1,66 |  UPC-E  |    11,12   |          48-57          |
    * |  2,67 |  EAN13  |    12,13   |          48-57          |
    * |  3,68 |   EAN8  |     7,8    |          48-57          |
    * |  4,69 |  CODE39 |      >1    | 32,36,37,43,45-57,65-90 |
    * |  5,70 |   I25   |  >1 (even) |          48-57          |
    * |  6,71 | CODEBAR |      >1    |    36,43,45-58,65-68    |
    * |  7,72 | CODE93  |      >1    |          0-127          |
    * |  8,73 | CODE128 |      >1    |          0-127          |
    * |  9,74 | CODE11  |      >1    |          48-57          |
    * | 10,75 |   MSI   |      >1    |          48-57          |
    * ----------------------------------------------------------
    * */
    fun printBarcode2(@IntRange(from = 65, to = 75) m: Int, data: String): ByteArray {

        val codeSelection = 123

        val codeA = 65
        val codeB = 66
        val codeC = 67

        val length = (data.length + 2)

        val data = byteArrayOf(codeSelection.toByte(), codeA.toByte(), *data.toByteArray())

        return byteArrayOf(
            AsciiByte.GS,
            AsciiByte.LOWERCASE_K,
            m.toByte()
//            ,
//            data.length.toByte(),
//            *data.toByteArray()
        )
    }

    /*
    * ESC 7 n1 n2 n3
    * Setting control parameter command
    *
    * Set "max heating dots", "heating time", "heating interval"
    * n1 = 0-255 Max printing dots Unit (8 dots), default: 7 (64 dots)
    * n2 = 3-255 Heating time, Unit (10μs), default: 80 (800μs)
    * n3 = 0-255 Heating interval, Unit (10μs), default: 2 (20μs)
    *
    * > The more max heating dots, the more peak current will cost when
    * printing, the faster printing speed. The max heating dots is 8*(n1+1)
    * > The more heating time, the more the more density, but the slower
    * printing speed. If heating time is too short, blank page may occur.
    * > The more heating interval, the more clear, but slower printing speed
    * */
    fun setControlParam(
        @IntRange(from = 0, to = 255) n1: Int,
        @IntRange(from = 3, to = 255) n2: Int,
        @IntRange(from = 0, to = 255) n3: Int
    ): ByteArray {
        return byteArrayOf(
            AsciiByte.ESC, AsciiByte.DIGIT_7,
            n1.toByte(), n2.toByte(), n3.toByte()
        )
    }

    /*
    * ESC 8 n1
    * Sleep parameter
    *
    * Setting the time for control board to enter sleep mode.
    * n1 = 0-255. The time waiting for sleep after printing finished.
    * Unit (Second), Default: 0 (Don't sleep)
    * When control board is in sleep mode, host must send one byte (0xff)
    * to wake up control board. And waiting 50ms, then send printing
    * command and data
    *
    * NOTE: This command is useful when the system is powered by battery.
    * */
    fun setSleepInterval(@IntRange(from = 0, to = 255) n1: Int): ByteArray {
        return byteArrayOf(AsciiByte.ESC, AsciiByte.DIGIT_8, n1.toByte())
    }

    /*
    * ESC 0 n1 n2 n3 d1 ...
    *
    * Setting bluetooth baud rate, name, password
    * n1 = 0-4 baud rate, default: 0
    *    0: 9600, 1: 19200, 2: 38400, 3: 57600, 4: 115200
    * n2 = the length of control board name for bluetooth
    * n3 = the length of control board password for bluetooth
    * d1...dk, k = n2 + n3
    *
    * NOTE: The command is valid only when the control board is
    * Bluetooth type control board
    * */
    fun setBluetoothParameter(
        @IntRange(from = 0, to = 4) baudRate: Int,
        name: String,
        password: String
    ): ByteArray {
        val n2 = name.length
        val n3 = password.length
        return byteArrayOf(
            AsciiByte.ESC, AsciiByte.DIGIT_0,
            baudRate.toByte(), n2.toByte(), n3.toByte(),
            *name.toByteArray(),
            *password.toByteArray()
        )
    }
}