<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>

    <xsl:param name="creation-date"/>
    <xsl:param name="modification-date"/>
    <xsl:param name="document-title"/>

    <!--    </xsl:template>-->
    <xsl:template match="/">
        <document>
            <metadata>
                <creation-date>
                    <xsl:value-of select="$creation-date"/>
                </creation-date>
                <modification-date>
                    <xsl:value-of select="$modification-date"/>
                </modification-date>
                <document-title>
                    <xsl:value-of select="$document-title"/>
                </document-title>
            </metadata>
            <content>
                <xsl:for-each select="//DIVISION">
                    <title>
                        <xsl:value-of select="TITLE/TI/P"/> - <xsl:value-of select="TITLE/STI/P"/>
                    </title>
                    <body>
                        <section>
                            <xsl:attribute name="title">
                                <xsl:value-of select="ARTICLE/TI.ART"/>
                            </xsl:attribute>
                            <xsl:for-each select="ARTICLE/PARAG">
                                <paragraph>
                                    <xsl:value-of select="ALINEA"/>
                                </paragraph>
                            </xsl:for-each>
                        </section>
                    </body>
                </xsl:for-each>
            </content>
        </document>
    </xsl:template>
</xsl:stylesheet>