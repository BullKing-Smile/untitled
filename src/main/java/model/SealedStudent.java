package model;

/**
 * @Author shenfei.wang@g42.ai
 * @Description
 * @Date 2025/2/24 20:28
 */
public sealed class SealedStudent permits FreshStudent, JuniorStudent, SeniorStudent{
    protected String StudentID;
    protected float enScore;
    protected float scScore;
    protected float peScore;
}
