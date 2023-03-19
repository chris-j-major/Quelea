<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xslt="http://www.w3.org/1999/XSL/Transform"
                exclude-result-prefixes="fo">
    <xsl:template match="schedule">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Roboto">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="my-page">
                    <fo:region-body margin="1in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="my-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="18pt" font-weight="bold" space-after="12pt">
                        <xsl:value-of select="title"/>
                    </fo:block>
                    <xsl:apply-templates select="song|passage"/>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    <xsl:template match="song">
        <fo:block space-after="10pt">
            <xsl:value-of select="position()"/>.
            <xsl:value-of select="title"/>
            <xsl:choose>
                <xsl:when test="author != ''">
                    - <xsl:value-of select="author"/>
                </xsl:when>
            </xsl:choose>
            <xsl:choose>
                <xsl:when test="ccli != ''">
                    (CCLI <xsl:value-of select="ccli"/>)
                </xsl:when>
            </xsl:choose>
        </fo:block>
    </xsl:template>
    <xsl:template match="passage">
        <fo:block space-after="10pt">
            <xsl:value-of select="position()"/>.
            <xsl:value-of select="@summary"/>
            <xsl:when test="@bible != ''">
                (<xsl:value-of select="@bible"/>)
            </xsl:when>
        </fo:block>
    </xsl:template>
</xsl:stylesheet>