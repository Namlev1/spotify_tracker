package com.spotifytracker.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotifytracker.model.Album;
import com.spotifytracker.model.Artist;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {
    String oldAlbumJson = "{\n" +
            "  \"href\": \"https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/albums?include_groups=album,single,compilation,appears_on&offset=0&limit=1&locale=en-US,en;q=0.6\",\n  \"limit\": 1,\n  \"next\": \"https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/albums?include_groups=album,single,compilation,appears_on&offset=1&limit=1&locale=en-US,en;q=0.6\",\n  \"offset\": 0,\n  \"previous\": null,\n  \"total\": 944,\n  \"items\": [\n    {\n      \"album_type\": \"album\",\n      \"total_tracks\": 15,\n      \"available_markets\": [\"AD\", \"AE\", \"AG\", \"AL\", \"AM\", \"AO\", \"AR\", \"AT\", \"AU\", \"AZ\", \"BA\", \"BB\", \"BD\", \"BE\", \"BF\", \"BG\", \"BH\", \"BI\", \"BJ\", \"BN\", \"BO\", \"BR\", \"BS\", \"BT\", \"BW\", \"BY\", \"BZ\", \"CA\", \"CD\", \"CG\", \"CH\", \"CI\", \"CL\", \"CM\", \"CO\", \"CR\", \"CV\", \"CW\", \"CY\", \"CZ\", \"DE\", \"DJ\", \"DK\", \"DM\", \"DO\", \"DZ\", \"EC\", \"EE\", \"EG\", \"ES\", \"ET\", \"FI\", \"FJ\", \"FM\", \"FR\", \"GA\", \"GB\", \"GD\", \"GE\", \"GH\", \"GM\", \"GN\", \"GQ\", \"GR\", \"GT\", \"GW\", \"GY\", \"HK\", \"HN\", \"HR\", \"HT\", \"HU\", \"ID\", \"IE\", \"IL\", \"IN\", \"IQ\", \"IS\", \"IT\", \"JM\", \"JO\", \"JP\", \"KE\", \"KG\", \"KH\", \"KI\", \"KM\", \"KN\", \"KR\", \"KW\", \"KZ\", \"LA\", \"LB\", \"LC\", \"LI\", \"LK\", \"LR\", \"LS\", \"LT\", \"LU\", \"LV\", \"LY\", \"MA\", \"MC\", \"MD\", \"ME\", \"MG\", \"MH\", \"MK\", \"ML\", \"MN\", \"MO\", \"MR\", \"MT\", \"MU\", \"MV\", \"MW\", \"MX\", \"MY\", \"MZ\", \"NA\", \"NE\", \"NG\", \"NI\", \"NL\", \"NO\", \"NP\", \"NR\", \"NZ\", \"OM\", \"PA\", \"PE\", \"PG\", \"PH\", \"PK\", \"PL\", \"PS\", \"PT\", \"PW\", \"PY\", \"QA\", \"RO\", \"RS\", \"RW\", \"SA\", \"SB\", \"SC\", \"SE\", \"SG\", \"SI\", \"SK\", \"SL\", \"SM\", \"SN\", \"SR\", \"ST\", \"SV\", \"SZ\", \"TD\", \"TG\", \"TH\", \"TJ\", \"TL\", \"TN\", \"TO\", \"TR\", \"TT\", \"TV\", \"TW\", \"TZ\", \"UA\", \"UG\", \"US\", \"UY\", \"UZ\", \"VC\", \"VE\", \"VN\", \"VU\", \"WS\", \"XK\", \"ZA\", \"ZM\", \"ZW\"],\n      \"external_urls\": {\n        \"spotify\": \"https://open.spotify.com/album/6nCJAxRvXmPkPiZo8Vh5ZG\"\n      },\n      \"href\": \"https://api.spotify.com/v1/albums/6nCJAxRvXmPkPiZo8Vh5ZG\",\n      \"id\": \"6nCJAxRvXmPkPiZo8Vh5ZG\",\n      \"images\": [\n        {\n          \"url\": \"https://i.scdn.co/image/ab67616d0000b273f0dd8e557b66318ea8e6978d\",\n          \"height\": 640,\n          \"width\": 640\n        },\n        {\n          \"url\": \"https://i.scdn.co/image/ab67616d00001e02f0dd8e557b66318ea8e6978d\",\n          \"height\": 300,\n          \"width\": 300\n        },\n        {\n          \"url\": \"https://i.scdn.co/image/ab67616d00004851f0dd8e557b66318ea8e6978d\",\n          \"height\": 64,\n          \"width\": 64\n        }\n      ],\n      \"name\": \"Libertad 548\",\n      \"release_date\": \"2019-09-27\",\n      \"release_date_precision\": \"day\",\n      \"type\": \"album\",\n      \"uri\": \"spotify:album:6nCJAxRvXmPkPiZo8Vh5ZG\",\n      \"album_group\": \"album\",\n      \"artists\": [\n        {\n          \"external_urls\": {\n            \"spotify\": \"https://open.spotify.com/artist/0TnOYISbd1XYRBk9myaseg\"\n          },\n          \"href\": \"https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg\",\n          \"id\": \"0TnOYISbd1XYRBk9myaseg\",\n          \"name\": \"Pitbull\",\n          \"type\": \"artist\",\n          \"uri\": \"spotify:artist:0TnOYISbd1XYRBk9myaseg\"\n        }\n      ]\n    }\n  ]\n}\n";
    String zeroFollowedArtistsJson = "{\n  \"artists\": {\n    \"href\": \"https://api.spotify.com/v1/me/following?type=artist&limit=20&locale=en-US,en;q=0.6\",\n    \"limit\": 20,\n    \"next\": null,\n    \"cursors\": {\n      \"after\": null\n    },\n    \"total\": 0,\n    \"items\": []\n  }\n}";
    String fiveArtistsJson = "{\n  \"artists\": {\n    \"href\": \"https://api.spotify.com/v1/me/following?type=artist&limit=5&locale=en-US,en;q=0.8\",\n    \"limit\": 5,\n    \"next\": \"https://api.spotify.com/v1/me/following?type=artist&limit=5&locale=en-US,en;q=0.8&after=15LsRgSmN0t8VLcsUFYW5J\",\n    \"cursors\": {\n      \"after\": \"15LsRgSmN0t8VLcsUFYW5J\"\n    },\n    \"total\": 15,\n    \"items\": [\n      {\n        \"external_urls\": {\n          \"spotify\": \"https://open.spotify.com/artist/0LX2VNf5w4iOHW1yyIqb74\"\n        },\n        \"followers\": {\n          \"href\": null,\n          \"total\": 1469130\n        },\n        \"genres\": [\"polish hip hop\", \"polish trap\"],\n        \"href\": \"https://api.spotify.com/v1/artists/0LX2VNf5w4iOHW1yyIqb74\",\n        \"id\": \"0LX2VNf5w4iOHW1yyIqb74\",\n        \"images\": [\n          {\n            \"url\": \"https://i.scdn.co/image/ab6761610000e5eb193d06090d101d3ee6617232\",\n            \"height\": 640,\n            \"width\": 640\n          },\n          {\n            \"url\": \"https://i.scdn.co/image/ab67616100005174193d06090d101d3ee6617232\",\n            \"height\": 320,\n            \"width\": 320\n          },\n          {\n            \"url\": \"https://i.scdn.co/image/ab6761610000f178193d06090d101d3ee6617232\",\n            \"height\": 160,\n            \"width\": 160\n          }\n        ],\n        \"name\": \"Bedoes 2115\",\n        \"popularity\": 66,\n        \"type\": \"artist\",\n        \"uri\": \"spotify:artist:0LX2VNf5w4iOHW1yyIqb74\"\n      },\n      {\n        \"external_urls\": {\n          \"spotify\": \"https://open.spotify.com/artist/0QR764k0D36npmTMWx5bft\"\n        },\n        \"followers\": {\n          \"href\": null,\n          \"total\": 1019021\n        },\n        \"genres\": [\"polish hip hop\", \"polish trap\"],\n        \"href\": \"https://api.spotify.com/v1/artists/0QR764k0D36npmTMWx5bft\",\n        \"id\": \"0QR764k0D36npmTMWx5bft\",\n        \"images\": [\n          {\n            \"url\": \"https://i.scdn.co/image/ab6761610000e5ebe6b03adf5bec0a6688db052a\",\n            \"height\": 640,\n            \"width\": 640\n          },\n          {\n            \"url\": \"https://i.scdn.co/image/ab67616100005174e6b03adf5bec0a6688db052a\",\n            \"height\": 320,\n            \"width\": 320\n          },\n          {\n            \"url\": \"https://i.scdn.co/image/ab6761610000f178e6b03adf5bec0a6688db052a\",\n            \"height\": 160,\n            \"width\": 160\n          }\n        ],\n        \"name\": \"Żabson\",\n        \"popularity\": 62,\n        \"type\": \"artist\",\n        \"uri\": \"spotify:artist:0QR764k0D36npmTMWx5bft\"\n      },\n      {\n        \"external_urls\": {\n          \"spotify\": \"https://open.spotify.com/artist/0kh9Gvy9lGZsq84x7I37DC\"\n        },\n        \"followers\": {\n          \"href\": null,\n          \"total\": 193317\n        },\n        \"genres\": [\"polish hip hop\", \"polish trap\"],\n        \"href\": \"https://api.spotify.com/v1/artists/0kh9Gvy9lGZsq84x7I37DC\",\n        \"id\": \"0kh9Gvy9lGZsq84x7I37DC\",\n        \"images\": [\n          {\n            \"url\": \"https://i.scdn.co/image/ab6761610000e5ebd21057ea4d93fdce0b930523\",\n            \"height\": 640,\n            \"width\": 640\n          },\n          {\n            \"url\": \"https://i.scdn.co/image/ab67616100005174d21057ea4d93fdce0b930523\",\n            \"height\": 320,\n            \"width\": 320\n          },\n          {\n            \"url\": \"https://i.scdn.co/image/ab6761610000f178d21057ea4d93fdce0b930523\",\n            \"height\": 160,\n            \"width\": 160\n          }\n        ],\n        \"name\": \"Beteo\",\n        \"popularity\": 58,\n        \"type\": \"artist\",\n        \"uri\": \"spotify:artist:0kh9Gvy9lGZsq84x7I37DC\"\n      },\n      {\n        \"external_urls\": {\n          \"spotify\": \"https://open.spotify.com/artist/154o9Wi0JpSDbYQPMtmpd5\"\n        },\n        \"followers\": {\n          \"href\": null,\n          \"total\": 42078\n        },\n        \"genres\": [\"polish hip hop\", \"polish trap\"],\n        \"href\": \"https://api.spotify.com/v1/artists/154o9Wi0JpSDbYQPMtmpd5\",\n        \"id\": \"154o9Wi0JpSDbYQPMtmpd5\",\n        \"images\": [\n          {\n            \"url\": \"https://i.scdn.co/image/ab6761610000e5eba65c7a674ea0aab941366bcd\",\n            \"height\": 640,\n            \"width\": 640\n          },\n          {\n            \"url\": \"https://i.scdn.co/image/ab67616100005174a65c7a674ea0aab941366bcd\",\n            \"height\": 320,\n            \"width\": 320\n          },\n          {\n            \"url\": \"https://i.scdn.co/image/ab6761610000f178a65c7a674ea0aab941366bcd\",\n            \"height\": 160,\n            \"width\": 160\n          }\n        ],\n        \"name\": \"Wiatr\",\n        \"popularity\": 41,\n        \"type\": \"artist\",\n        \"uri\": \"spotify:artist:154o9Wi0JpSDbYQPMtmpd5\"\n      },\n      {\n        \"external_urls\": {\n          \"spotify\": \"https://open.spotify.com/artist/15LsRgSmN0t8VLcsUFYW5J\"\n        },\n        \"followers\": {\n          \"href\": null,\n          \"total\": 133043\n        },\n        \"genres\": [\"black metal\", \"polish black metal\", \"polish metal\", \"voidgaze\"],\n        \"href\": \"https://api.spotify.com/v1/artists/15LsRgSmN0t8VLcsUFYW5J\",\n        \"id\": \"15LsRgSmN0t8VLcsUFYW5J\",\n        \"images\": [\n          {\n            \"url\": \"https://i.scdn.co/image/ab6761610000e5ebed77e8164cf6633ab8715db4\",\n            \"height\": 640,\n            \"width\": 640\n          },\n          {\n            \"url\": \"https://i.scdn.co/image/ab67616100005174ed77e8164cf6633ab8715db4\",\n            \"height\": 320,\n            \"width\": 320\n          },\n          {\n            \"url\": \"https://i.scdn.co/image/ab6761610000f178ed77e8164cf6633ab8715db4\",\n            \"height\": 160,\n            \"width\": 160\n          }\n        ],\n        \"name\": \"Batushka\",\n        \"popularity\": 37,\n        \"type\": \"artist\",\n        \"uri\": \"spotify:artist:15LsRgSmN0t8VLcsUFYW5J\"\n      }\n    ]\n  }\n}";
    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void extractFiveArtists() throws IOException {
        JsonNode node = objectMapper.readTree(fiveArtistsJson);
        List<Artist> artistList = JsonUtil.extractArtists(node);
        assertEquals(5, artistList.size());
        assertEquals("Bedoes 2115", artistList.get(0).getName());
        assertEquals("Żabson", artistList.get(1).getName());
        assertEquals("Beteo", artistList.get(2).getName());
        assertEquals("Wiatr", artistList.get(3).getName());
        assertEquals("Batushka", artistList.get(4).getName());
    }

    @Test
    void zeroFollowedArtists() throws IOException {
        JsonNode node = objectMapper.readTree(zeroFollowedArtistsJson);
        List<Artist> albumList = JsonUtil.extractArtists(node);
        assertEquals(0, albumList.size());
    }
    @Test
    void skipOldAlbum() throws IOException {
        JsonNode node = objectMapper.readTree(oldAlbumJson);
        List<Album> albumList = JsonUtil.extractRecentAlbums(node);
        assertEquals(0, albumList.size());
    }
}