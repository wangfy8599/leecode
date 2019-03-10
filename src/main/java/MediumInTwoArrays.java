import com.google.common.base.Preconditions;

public class MediumInTwoArrays {

    public static double findSimple(int[] nums) {
        boolean odd = nums.length % 2 == 1;
        int m = (nums.length - 1) / 2;
        if(odd) {
            return nums[m];
        }
        return (double)(nums[m] + nums[m+1])/2;
    }

    private static String arrayToString(int[] nums) {
        StringBuilder builder = new StringBuilder("{");
        for(int i=0; i<nums.length; ++i) {
            if(i > 0) {
                builder.append(",");
            }
            builder.append(nums[i]);
        }
        builder.append("}");
        return builder.toString();
    }

    private static boolean checkLeft(int[] nums1, int ai, int[] nums2, int bi, int mid) {
        if(ai >= 0) {
            if(nums1[ai] > mid) {
                return false;
            }
        }
        if(bi >= 0) {
            if(nums2[bi] > mid) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkRight(int[] nums1, int aj, int[] nums2, int bj, int mid) {
        if(aj < nums1.length) {
            if(nums1[aj] < mid) {
                return false;
            }
        }
        if(bj < nums2.length) {
            if(nums2[bj] < mid) {
                return false;
            }
        }
        return true;
    }

    public static double findInArray(int[] nums, int i, boolean odd) {
        if(odd) {
            return nums[i];
        }
        return (double)(nums[i] + nums[i+1])/2;
    }

    public static double findInArray(int[] nums, int i, int n, boolean odd) {
        if(odd) {
            return Math.min(nums[i], n);
        }
        if(n < nums[i + 1]) {
            return (double)(n + nums[i])/2;
        }
        return (double)(nums[i] + Math.min(n, nums[i+1]))/2;
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if(nums1.length == 0) return findSimple(nums2);
        if(nums2.length == 0) return findSimple(nums1);

        System.out.printf("************ %s - %s\n", arrayToString(nums1), arrayToString(nums2));

        int totalNumber = nums1.length + nums2.length;
        boolean odd = totalNumber % 2 == 1;
        int totalLeft = (totalNumber - 1) / 2;

        int ai = 0, aj = nums1.length - 1;
        int bi = 0, bj = nums2.length - 1;

        boolean adjusted = false;

        while(true) {
            if(ai > aj) {
                return findInArray(nums2, totalLeft - ai, odd);
            }
            if(bi > bj) {
                return findInArray(nums1, totalLeft - bi, odd);
            }
            if(ai < 0) {
                return findInArray(nums2, totalLeft, odd);
            }
            if(bi < 0) {
                return findInArray(nums1, totalLeft, odd);
            }
            if(adjusted) {
                if(bi == totalLeft && bi + 1 < nums2.length) {
                    return findInArray(nums2, totalLeft, nums1[ai], odd);
                } else if(ai == totalLeft && ai + 1 < nums1.length) {
                    return findInArray(nums1, totalLeft, nums2[bi], odd);
                }
            }
            int am = 0;
            int bm = 0;
            if(ai == aj) {
                am = ai;
                bm = totalLeft - am;
            } else if(bi == bj) {
                bm = bi;
                am = totalLeft - bm;
            } else {
                am = (ai + aj)/2;
                if(am > totalLeft - bi) {
                    am = totalLeft - bi;
                    bm = bi;
                } else {
                    bm = totalLeft - am;
                    if(bm > nums2.length - 1) {
                        bm = (bi + bj)/2;
                        if(bm > totalLeft - ai) {
                            bm = totalLeft - ai;
                            am = ai;
                        } else {
                            am = totalLeft - bm;
                        }
                    }
                }
            }

            System.out.printf("{%d - %d}-----({%d - %d}), ({%d - %d})", am, bm, ai, aj, bi, bj);
            System.out.printf("------(%d - %d), (%d - %d)\n", nums1[ai], nums1[aj], nums2[bi], nums2[bj]);

            int min = Math.min(nums1[am], nums2[bm]);
            int max = Math.max(nums1[am], nums2[bm]);
            if(checkLeft(nums1, am - 1, nums2, bm - 1, min) && checkRight(nums1, am + 1, nums2, bm + 1, max)) {
                return odd ? min : (double)(nums1[am] + nums2[bm])/2;
            }

            if(nums1[am] <= nums2[bm]) {
                ai = am;
                if(bm - 1 >= 0 && nums1[am] < nums2[bm - 1]) {
                    ++ai;
                }
                if(am + 1 < nums1.length && nums1[am + 1] > nums2[bm]) {
                    aj = am;
                } else if(ai + bi == totalLeft && ai + 1 < nums1.length && nums1[ai + 1] <= nums2[bm]) {
                    if(bm >= 1 && nums2[bm - 1] <= nums1[ai + 1]) {
                        bi = bm - 1;
                        bj = bm - 1;
                    } else {
                        bj = -1;
                    }
                } else if (bj == bm){
                    if(bj == 0) {
                        bi = -1;
                    } else if (bi < bm) {
                        bj = bm - 1;
                        if(nums1[am] > nums2[bj]) {
                            bi = bm;
                        }
                    }
                } else {
                    bj = bm;
                }
            } else {
                bi = bm;
                if(am - 1 >= 0 && nums2[bm] < nums1[am - 1]) {
                    ++bi;
                }
                if(bm + 1 < nums2.length && nums2[bm + 1] > nums1[am]) {
                    bj = bm;
                } else if(ai + bi == totalLeft && bi + 1 < nums2.length && nums2[bi + 1] <= nums1[am]) {
                    if(am >= 1 && nums1[am - 1] <= nums2[bi + 1]) {
                        ai = am - 1;
                        aj = am - 1;
                    } else {
                        aj = -1;
                    }
                } else if(aj == am) {
                    if(aj == 0) {
                        ai = -1;
                    } else if (ai < am) {
                        aj = am - 1;
                        if(nums2[bm] > nums1[aj]) {
                            ai = am;
                        }
                    }
                } else {
                    aj = am;
                }
            }
            adjusted = true;

            System.out.printf("{%d - %d}-----({%d - %d}), ({%d - %d})\n", am, bm, ai, aj, bi, bj);
        }
    }

    public static void findM(int[] a, int[] b, double expect) {
        double result = findMedianSortedArrays(a, b);
        Preconditions.checkState(result == expect, String.format("%f != %f", result, expect));
        result = findMedianSortedArrays(b, a);
        Preconditions.checkState(result == expect, String.format("%f != %f", result, expect));
    }

    public static void main(String[] args) throws Exception {
        findM(new int[]{4,6}, new int[]{1,2,3,5}, 3.5f);

        findM(new int[]{}, new int[]{1}, 1f);
        findM(new int[]{}, new int[]{2,4}, 3f);
        findM(new int[]{1}, new int[]{2}, 1.5f);
        findM(new int[]{1}, new int[]{2,3}, 2f);
        findM(new int[]{1,2}, new int[]{3}, 2f);
        findM(new int[]{1,3}, new int[]{2}, 2f);
        findM(new int[]{1,2}, new int[]{3,4}, 2.5f);
        findM(new int[]{1,3}, new int[]{2,4}, 2.5f);
        findM(new int[]{1,4}, new int[]{2,3}, 2.5f);
        findM(new int[]{1,9}, new int[]{2,4,6,8}, 5f);
        findM(new int[]{3,6}, new int[]{1,2,4,5}, 3.5f);
        findM(new int[]{1,2}, new int[]{3,4,5,6,7,8}, 4.5f);
        findM(new int[]{6,7}, new int[]{1,2,3,4,5,8}, 4.5f);
        findM(new int[]{1,2,3}, new int[]{4,5}, 3f);
        findM(new int[]{1,2,5}, new int[]{3,4}, 3f);
        findM(new int[]{1,2,5}, new int[]{3,4,6}, 3.5f);
        findM(new int[]{1,2,4,6}, new int[]{3,5}, 3.5f);
        findM(new int[]{1,3,5,7}, new int[]{2,4,6,8}, 4.5f);
        findM(new int[]{1,2,3,4,5}, new int[]{6,7,8}, 4.5f);
        findM(new int[]{1,2,3,4,5}, new int[]{6,7,8,9,10}, 5.5f);
        findM(new int[]{1,3,5,7,9}, new int[]{2,4,6,8}, 5f);
        findM(new int[]{1,2,3,4,5,6}, new int[]{7,8}, 4.5f);
        findM(new int[]{1,3,5,7,9,11}, new int[]{2,4,6,8}, 5.5f);
        findM(new int[]{2,3,4,7,8,9,10}, new int[]{1,5,6}, 5.5f);
        findM(new int[]{1,3,5,7,9,11,12,13}, new int[]{2,4,6,8}, 6.5f);

        findM(new int[]{1,1}, new int[]{1,2}, 1f);

        findM(new int[]{9,28,245,307,461,561,680,683,688,711,728,755,789,880,937,962,1154,1166,1179,1259,1315,1331,1459,1545,1638,1642,1652,1658,1670,1877,2002,2015,2046,2109,2159,2162,2180,2197,2316,2338,2372,2419,2423,2529,2628,2636,2745,2793,2818,2833,2962,3009,3016,3080,3170,3258,3270,3337,3362,3452,3693,3788,3802,3979,4044,4070,4074,4074,4353,4355,4439,4586,4626,4716,4719,4790,4887,4924,4925,4929,4992,5034,5051,5121,5121,5320,5336,5518,5615,5700,5700,5716,5751,5905,5911,5947,5997,6015,6045,6153,6180,6226,6256,6321,6321,6340,6588,6726,6735,6886,6955,6964,6980,6998,7007,7058,7150,7274,7293,7294,7341,7354,7359,7427,7433,7511,7593,7887,7943,8033,8217,8246,8248,8260,8270,8288,8307,8370,8398,8435,8446,8460,8505,8526,8565,8806,8824,8872,8904,8992,9035,9037,9048,9113,9115,9274,9316,9336,9432,9445,9466,9562,9582,9592,9803,9897,9924,9999,10015,10043,10149,10214,10260,10309,10320,10323,10350,10415,10510,10613,10671,10712,10726,10769,10824,10832,11015,11056,11059,11067,11082,11221,11268,11271,11299,11325,11327,11339,11377,11390,11440,11560,11656,11740,11756,11830,11839,11843,11882,11932,11934,11960,11962,12075,12141,12163,12190,12314,12327,12379,12420,12477,12511,12512,12724,12802,12843,12914,12952,12968,13006,13049,13073,13239,13298,13428,13471,13496,13592,13641,13659,13795,13812,13841,13885,13949,14043,14120,14150,14173,14230,14250,14278,14298,14441,14455,14456,14584,14671,14735,14889,14914,14946,14949,14978,14993,15175,15239,15280,15565,15577,15582,15637,15652,15699,15701,15772,15841,15844,15854,15936,16060,16141,16175,16182,16195,16241,16287,16370,16397,16461,16516,16718,16739,16752,16795,16859,16911,16943,17054,17062,17064,17065,17065,17091,17093,17101,17147,17186,17240,17265,17326,17473,17499,17518,17552,17610,17628,17655,17774,17821,17847,17854,17949,18012,18070,18086,18103,18122,18130,18249,18260,18442,18541,18569,18735,18778,18910,18976,18986,19023,19116,19140,19156,19183,19194,19217,19227,19252,19290,19311,19344,19414,19479,19524,19571,19633,19648,19651,19723,19728,19733,19756,19815,19853,19944,20042,20155,20208,20238,20246,20255,20382,20413,20438,20470,20583,20590,20611,20642,20715,20755,20919,20948,20958,21120,21210,21271,21351,21356,21380,21436,21496,21544,21640,21709,21721,21724,21733,21860,21879,21948,21953,21955,21992,21994,22028,22124,22128,22131,22191,22221,22225,22487,22548,22560,22640,22640,22643,22656,22691,22769,22928,22935,23001,23007,23011,23053,23080,23159,23165,23236,23243,23297,23425,23449,23480,23553,23565,23646,23673,23711,23816,23882,23882,24009,24020,24036,24175,24236,24262,24288,24322,24372,24416,24429,24668,24785,24828,24883,25008,25079,25107,25205,25470,25539,25569,25633,25709,25719,25817,25873,25953,26026,26049,26132,26139,26229,26338,26385,26473,26511,26536,26538,26635,26678,26754,26846,26884,26994,27015,27336,27424,27510,27536,27589,27701,27749,27810,27950,27989,27989,27991,28006,28028,28089,28137,28174,28193,28198,28257,28261,28363,28434,28466,28479,28531,28547,28586,28650,28696,28780,28816,28839,28855,29005,29108,29162,29240,29282,29335,29381,29384,29491,29573,29673,29677,29710,29938,29984,30051,30089,30108,30141,30177,30358,30396,30560,30584,30674,30675,30678,30828,30954,30994,31132,31193,31279,31346,31355,31466,31514,31595,31690,31843,31923,32060,32175,32230,32233,32241,32338,32370,32410,32456,32460,32601,32689,32761}
                ,new int[]{20,481,516,770,1063,1247,1275,1469,1681,1974,2093,2324,2440,2548,2553,2759,2990,3032,3578,3813,3869,4019,4713,4741,4744,4771,4777,4863,5009,5266,5307,5476,5549,5673,5740,5925,6049,6364,6455,6565,6706,7094,7129,7259,7347,7364,7578,7635,7791,7793,7837,7863,8046,8046,8346,8535,8655,8946,9079,9594,10638,10991,11110,11167,11603,11718,11857,11960,12057,12109,12240,12287,12412,12565,12610,12680,12701,12814,12907,13128,13170,13385,13493,13572,13640,13947,13986,14139,14173,14319,14382,14407,14473,14497,14949,15038,15056,15416,15523,15649,15921,16451,16542,16579,16723,16894,17010,17410,17452,17725,17925,18070,18298,18329,18438,18741,18808,19247,19330,19778,19875,20595,20631,20765,20782,20998,21135,21525,21576,21622,21755,21891,21920,22097,22196,22770,22791,22841,23024,23066,23074,23094,23509,23863,24014,24101,24194,24360,24426,24499,24523,26026,26036,26237,26282,26380,26399,26883,27276,27304,27464,27482,27531,27571,27693,27736,27948,27950,28015,28029,28034,28369,28484,28538,28610,28845,29019,29122,29142,29173,29251,29304,29342,29517,29595,29731,29750,29830,29961,29987,30053,30374,30398,30510,30633,30661,30748,31278,31310,31850,31919,32175,32239,32261,32433,32457,32504,32761}
                ,16516f);
    }
}
